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


package edu.ur.ir.web.action.researcher;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

import edu.ur.exception.DuplicateNameException;
import edu.ur.ir.researcher.Researcher;
import edu.ur.ir.researcher.ResearcherFolder;
import edu.ur.ir.researcher.ResearcherService;

/**
 * Action to add a folder to the researcher page.
 * 
 * @author Sharmila Ranganarhan
 *
 */
public class AddResearcherFolder extends ActionSupport {
	
	/** User file system service. */
	private ResearcherService researcherService;
	
	/** the name of the folder to add */
	private String folderName;
	
	/** Description of the folder */
	private String folderDescription;
	
	/** Current folder the user is looking at  */
	private Long parentFolderId;
	
	/** Id of the folder to update for updating  */
	private Long updateFolderId;
	
	/**  Eclipse generated id */
	private static final long serialVersionUID = -6343965003122766186L;
	
	/**  Logger for add researcher folder action */
	private static final Logger log = Logger.getLogger(AddResearcherFolder.class);
	
	/**  Researcher object */
	private Long researcherId;

	/**  Indicates the folder has been added*/
	private boolean added = false;
	
	/** Message that can be displayed to the user. */
	private String message;
	
	/**
	 * Create the new folder
	 */
	public String save()throws Exception
	{
		log.debug("creating a researcher folder parent folderId = " + parentFolderId);
		added = false;
		// assume that if the current folder id is null or equal to 0
		// then we are adding a root folder to the user.
		if(parentFolderId == null || parentFolderId == 0)
		{
			added = addRootFolder();
		}
		else
		{
			added = addSubFolder();
		}
		
		if( !added)
		{
			message = getText("researcherFolderAlreadyExists", new String[]{folderName});
		}
        return SUCCESS;
	}
	
	/**
	 * Update a folder with the given information.
	 * 
	 * @return success if the folder is updated.
	 * @throws Exception
	 */
	public String updateFolder()throws Exception
	{
		
		log.debug("updating a researcher folder parent folderId = " + parentFolderId);
		added = false;

		ResearcherFolder other = null;
		
		// check the name.  This makes sure that 
		// if the name has been changed, it does not conflict
		// with a folder already in the folder system.
		if( parentFolderId == null || parentFolderId == 0)
		{
			other = researcherService.getRootResearcherFolder(folderName, researcherId);
		}
		else
		{
			other = researcherService.getResearcherFolder(folderName, parentFolderId);
		}
		
		// name has been changed and does not conflict
		if( other == null)
		{
			ResearcherFolder existingFolder = researcherService.getResearcherFolder(updateFolderId, true);
			existingFolder.setName(folderName);
			existingFolder.setDescription(folderDescription);
			researcherService.saveResearcherFolder(existingFolder);
			added = true;
		}
		// name has not been changed
		else if(other.getId().equals(updateFolderId))
		{
			other.setDescription(folderDescription);
			researcherService.saveResearcherFolder(other);
			added = true;
		}

		if( !added)
		{
			message = getText("researcherFolderAlreadyExists", new String[]{folderName});
		}
		
			
	    return SUCCESS;
		
	}

	/**
	 * Get the name of the folder to add.
	 * 
	 * @return
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * Set the name of the folder to add.
	 * 
	 * @param folderName
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * Current folder the user is looking at.
	 * 
	 * @return
	 */
	public Long getParentFolderId() {
		return parentFolderId;
	}

	/**
	 * The current folder the user is looking at.
	 * 
	 * @param currentFolderId
	 */
	public void setParentFolderId(Long currentFolderId) {
		this.parentFolderId = currentFolderId;
	}

	
	/**
	 * Creates a new root folder 
	 * 
	 */
	private boolean addRootFolder()  
	{
		 boolean added = false;
		 Researcher researcher = researcherService.getResearcher(researcherId, false);
		 if( researcher.getRootFolder(folderName) == null )
	     {
			 ResearcherFolder researcherFolder = null;
			 try {
				researcherFolder = researcher.createRootFolder(folderName);
				researcherFolder.setDescription(folderDescription);
				researcherService.saveResearcherFolder(researcherFolder);
				 
				added = true;
			 } catch (DuplicateNameException e) {
				throw new RuntimeException("Fix this move error");
			 }

         }
		 return added;
	}
	
	/**
	 * Adds a sub folder to an existing folder
	 */
	private boolean addSubFolder() 
	{
		boolean added = false;
		ResearcherFolder folder = researcherService.getResearcherFolder(parentFolderId, true);
	
		try
		{
		    ResearcherFolder researcherFolder = folder.createChild(folderName);
		    researcherFolder.setDescription(folderDescription);
		    researcherService.saveResearcherFolder(folder);
		    added = true;
		}
		catch(DuplicateNameException e)
		{
			added = false;
		}
		return added;
	}
	
	/**
	 * Indicates if the folder has been added 
	 * 
	 * @return
	 */
	public boolean isAdded() {
		return added;
	}

	/**
	 * Get the folder added message.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Description of the folder.
	 * 
	 * @return
	 */
	public String getFolderDescription() {
		return folderDescription;
	}

	/**
	 * Description of the folder.
	 * 
	 * @param folderDescription
	 */
	public void setFolderDescription(String folderDescription) {
		this.folderDescription = folderDescription;
	}

	public Long getUpdateFolderId() {
		return updateFolderId;
	}

	public void setUpdateFolderId(Long updateFolderId) {
		this.updateFolderId = updateFolderId;
	}


	public ResearcherService getResearcherService() {
		return researcherService;
	}

	public void setResearcherService(ResearcherService researcherService) {
		this.researcherService = researcherService;
	}

	public Long getResearcherId() {
		return researcherId;
	}

	public void setResearcherId(Long researcherId) {
		this.researcherId = researcherId;
	}
}
