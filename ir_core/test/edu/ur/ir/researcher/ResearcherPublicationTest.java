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

package edu.ur.ir.researcher;

import org.testng.annotations.Test;

import edu.ur.ir.item.GenericItem;
import edu.ur.ir.user.IrUser;

/**
 * Test the Researcher Publication class
 * 
 * @author Sharmila Ranganathan
 * 
 */
@Test(groups = { "baseTests" }, enabled = true)
public class ResearcherPublicationTest {
	
	
	/**
	 * Test the basic get and set  methods
	 * 
	 * @param description
	 */
	public void testBasicSets() 
	{
		GenericItem genericItem = new GenericItem("genericItem");
		
		// create the owner of the personal item
		IrUser user = new IrUser("nate", "password");
		Researcher researcher = new Researcher(user); 

		ResearcherPublication researcherPublication = new ResearcherPublication(researcher, genericItem, 1);
		assert researcherPublication.getPublication() != null : "Should be able to find publication";
		
		assert researcherPublication.getPublication().equals(genericItem) : "Publications should be equal";
	    assert researcherPublication.getResearcher().equals(researcher): "Researchers should be equal";
	}

}
