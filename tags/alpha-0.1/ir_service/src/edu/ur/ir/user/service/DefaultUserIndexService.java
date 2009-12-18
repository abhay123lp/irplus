/**  
   Copyright 2008 University of Rochester

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/  


package edu.ur.ir.user.service;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import edu.ur.ir.NoIndexFoundException;
import edu.ur.ir.person.PersonName;
import edu.ur.ir.user.IrUser;
import edu.ur.ir.user.UserEmail;
import edu.ur.ir.user.UserIndexService;

/**
 * This updates the specified index with the given user information
 * 
 * @author Nathan Sarr
 *
 */
public class DefaultUserIndexService implements UserIndexService{
	
	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";
	public static final String USER_FIRST_NAME = "user_first_name";
	public static final String USER_LAST_NAME = "user_last_name";
	public static final String USER_EMAILS = "user_emails";
	public static final String USER_DEPARTMENTS = "user_departments";
	public static final String USER_NAMES = "user_names";
	
	/** Analyzer for dealing with text indexing */
	private Analyzer analyzer;
	
	/**  Get the logger for this class */
	private static final Logger log = Logger.getLogger(DefaultUserIndexService.class);

	/**
	 * Analyzer used to analyze the file.
	 * 
	 * @return the analyzer used to analyze the information
	 */
	public Analyzer getAnalyzer() {
		return analyzer;
	}

	/**
	 * Set the analyzer user to index the information.
	 * s
	 * @param analyzer
	 */
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
	
	
	/**
	 * Add the user to the index.
	 * 
	 * @see edu.ur.ir.user.UserIndexService#addToIndex(edu.ur.ir.user.IrUser, java.io.File)
	 */
	public void addToIndex(IrUser user, File userIndexFolder)
			throws NoIndexFoundException {
		
		if( log.isDebugEnabled() )
		{
			log.debug("adding user: " + user + " to index folder " + userIndexFolder.getAbsolutePath());
		}
		
		if( !userIndexFolder.exists())
		{
			throw new NoIndexFoundException("the folder " + userIndexFolder.getAbsolutePath() + " could not be found");
		}
		
		Document doc = new Document();
        
	    doc.add(new Field(USER_ID, 
			user.getId().toString(), 
			Field.Store.YES, 
			Field.Index.UN_TOKENIZED));
	    
	    doc.add(new Field(USER_NAME, 
				user.getUsername(), 
				Field.Store.YES, 
				Field.Index.TOKENIZED));
	    
	    if( user.getFirstName() != null )
	    {
	        doc.add(new Field(USER_FIRST_NAME, 
				user.getFirstName(), 
				Field.Store.YES, 
				Field.Index.TOKENIZED));
	    }
	    
	    if( user.getLastName() != null )
	    {
	        doc.add(new Field(USER_LAST_NAME, 
				user.getLastName(), 
				Field.Store.YES, 
				Field.Index.TOKENIZED));
	    }
	    
	    String emails = "";
	    for( UserEmail email : user.getUserEmails())
	    {
	    	if( email != null )
	    	{
	    		emails += email.getEmail() + " ";
	    	}
	    }
	    
	    doc.add(new Field(USER_EMAILS, 
				emails, 
				Field.Store.YES, 
				Field.Index.TOKENIZED));
	    
	    if( user.getDepartment() != null )
	    {
	    	doc.add(new Field(USER_DEPARTMENTS, 
					user.getDepartment().getName(), 
					Field.Store.YES, 
					Field.Index.TOKENIZED));
	    }

	    if (user.getPersonNameAuthority() != null) {
		    StringBuffer names =  new StringBuffer();
		    
		    for( PersonName personName : user.getPersonNameAuthority().getNames()) {
	
				if( personName.getForename() != null )
				{
					names.append(" " + personName.getForename() + " ");
				}

				if( personName.getMiddleName()!= null )
				{
					names.append(personName.getMiddleName() + " ");
				}

				if( personName.getFamilyName() != null )
				{
					names.append(personName.getFamilyName() + " ");
				}

				if( personName.getSurname() != null )
				{
					names.append(personName.getSurname() + " ");
				}
				
				names.append(":");
			}

		    doc.add(new Field(USER_NAMES, 
					names.toString(), 
					Field.Store.YES, 
					Field.Index.TOKENIZED));
	    }
	    
	    writeDocument(userIndexFolder.getAbsolutePath(), doc);
	}

	
	/**
	 * Delete the user from the specified index.
	 * 
	 * @see edu.ur.ir.user.UserIndexService#deleteFromIndex(edu.ur.ir.user.IrUser, java.io.File)
	 */
	public void deleteFromIndex(IrUser user, File userIndexFolder) {
		if( log.isDebugEnabled() )
		{
			log.debug("deleting user: " + user + " from index folder " + userIndexFolder.getAbsolutePath());
		}
		// if the user does not have an index folder
		// don't need to do anything.
		if( userIndexFolder == null || !userIndexFolder.exists() || userIndexFolder.list() == null ||
				userIndexFolder.list().length == 0)
		{
			return;
		}
		
		Directory directory = null;
		IndexReader reader = null;
		try {
			synchronized(this)
			{
			    directory = FSDirectory.getDirectory(userIndexFolder.getAbsolutePath());
			    if( IndexReader.isLocked(directory) )
			    {
				    throw new RuntimeException("Users index directory " + userIndexFolder.getAbsolutePath() +
						" is locked ");
			    }
			    else
			    {
				    reader = IndexReader.open(directory);
				    Term term = new Term(USER_ID, user.getId().toString());
			        reader.deleteDocuments(term);
			        reader.close();
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if( reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		
	}

	
	/**
	 * Update the index with the specified existing user.
	 * 
	 * @see edu.ur.ir.user.UserIndexService#updateIndex(edu.ur.ir.user.IrUser, java.io.File)
	 */
	public void updateIndex(IrUser user, File userIndexFolder)
			throws NoIndexFoundException {
		if( log.isDebugEnabled() )
		{
			log.debug("updating index for user: " + user + " in index folder " + userIndexFolder.getAbsolutePath());
		}
		deleteFromIndex(user, userIndexFolder);
		addToIndex(user, userIndexFolder);
	}
	
	/**
	 * Write the document to the index in the directory.
	 * 
	 * @param directoryPath - location where the directory exists.
	 * @param documents - documents to add to the directory.
	 */
	private void writeDocument(String directoryPath, Document document)
	{
		log.debug("write document to directory " + directoryPath );
		IndexWriter writer = null;
		try {
			synchronized(this)
			{
			    Directory directory = FSDirectory.getDirectory(directoryPath);
			    writer = new IndexWriter(directory, analyzer);
			    writer.addDocument(document);
			    writer.flush();
			    writer.optimize();
			}
		} catch (IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	    finally {
		    if (writer != null) {
			    try {
				    writer.close();
			    } catch (Exception e) {
				    log.error(e);
			    }
		    }
	    }
	}

}