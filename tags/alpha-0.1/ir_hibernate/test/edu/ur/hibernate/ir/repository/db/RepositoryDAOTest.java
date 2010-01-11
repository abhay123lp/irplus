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

package edu.ur.hibernate.ir.repository.db;

import java.io.File;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.annotations.Test;

import edu.ur.file.db.FileInfo;
import edu.ur.hibernate.ir.test.helper.ContextHolder;
import edu.ur.hibernate.ir.test.helper.PropertiesLoader;
import edu.ur.hibernate.ir.test.helper.RepositoryBasedTestHelper;
import edu.ur.ir.IllegalFileSystemNameException;
import edu.ur.ir.file.IrFile;
import edu.ur.ir.file.IrFileDAO;
import edu.ur.ir.repository.Repository;
import edu.ur.ir.repository.RepositoryDAO;
import edu.ur.util.FileUtil;

/**
 * Test the persistance methods for Repository Information
 * 
 * @author Nathan Sarr
 * 
 */
@Test(groups = { "baseTests" }, enabled = true)
public class RepositoryDAOTest {

	/** get the application context */
	ApplicationContext ctx = ContextHolder.getApplicationContext();
	
	/** Properties file with testing specific information. */
	PropertiesLoader propertiesLoader = new PropertiesLoader();
	
	/** Get the properties file  */
	Properties properties = propertiesLoader.getProperties();
	
	/** Repository relational data access  */
	RepositoryDAO repositoryDAO = (RepositoryDAO) ctx.getBean("repositoryDAO");

	/** Ir File relational data access.  */
	IrFileDAO irFileDAO = (IrFileDAO) ctx.getBean("irFileDAO");
	
	/** Platform transaction manager  */
	PlatformTransactionManager tm = (PlatformTransactionManager)ctx.getBean("transactionManager");

	/** Basic tranaction definition */
	TransactionDefinition td = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
	
	/**
	 * Test Repository persistance
	 */
	@Test
	public void baseRepositoryDAOTest() {

		TransactionStatus ts = tm.getTransaction(td);
		
		// create a repository to store files in.
		RepositoryBasedTestHelper repoHelper = new RepositoryBasedTestHelper(ctx);
		Repository repo = repoHelper.createRepository("localFileServer", 
				"displayName",
				"file_database", 
				"my_repository", 
				properties.getProperty("a_repo_path"),
				"default_folder");

		
		tm.commit(ts);
		
		Long id = repo.getId();
		//create a new transaction
		ts = tm.getTransaction(td);
		// make sure we can find a repository by id
		Repository other = repositoryDAO.getById(id, false);
		assert other != null : "Should be able to find " +
		"the repository by id";
		
		assert other.equals(repo) : "Repositories should be equal";		
		tm.commit(ts);		
	
		//create a new transaction
		ts = tm.getTransaction(td);
		repoHelper.cleanUpRepository();
		tm.commit(ts);	
		
	}
	
	
	/**
	 * Test adding pictures
	 */
	@Test
	public void addPictureRepositoryDAOTest()  throws IllegalFileSystemNameException{

		TransactionStatus ts = tm.getTransaction(td);
		
		// create a repository to store files in.
		RepositoryBasedTestHelper repoHelper = new RepositoryBasedTestHelper(ctx);
		Repository repo = repoHelper.createRepository("localFileServer", 
				"displayName",
				"file_database", 
				"my_repository", 
				properties.getProperty("a_repo_path"),
				"default_folder");

		
		tm.commit(ts);
		
		Long id = repo.getId();
		
		
		
		//create a new transaction
		ts = tm.getTransaction(td);
		// make sure we can find a repository by id
		Repository other = repositoryDAO.getById(id, false);
		
		
		// create the first file to store in the temporary folder
		String tempDirectory = properties.getProperty("ir_hibernate_temp_directory");
		File directory = new File(tempDirectory);
		
        // helper to create the file
		FileUtil testUtil = new FileUtil();
		testUtil.createDirectory(directory);
		
		File f = testUtil.creatFile(directory, "testFile", 
				"Hello  - irFile This is text in a file");

		FileInfo fileInfo1 = repo.getFileDatabase().addFile(f, "newFile1");
		fileInfo1.setDisplayName("displayName1");
		fileInfo1.setDescription("testThis");
		IrFile irFile = new IrFile(fileInfo1, "newName");
		
		irFileDAO.makePersistent(irFile);
		
		other.addPicture(irFile);
		repositoryDAO.makePersistent(other);
	
		tm.commit(ts);		
		
		//create a new transaction
		ts = tm.getTransaction(td);
		//reload the repository
		other = repositoryDAO.getById(id, false);
		
		assert other.getPicture(irFile.getId()) != null : "The picture should be found";
		
		tm.commit(ts);	
		//create a new transaction
		ts = tm.getTransaction(td);
		repoHelper.cleanUpRepository();
		tm.commit(ts);	
		
	}
}