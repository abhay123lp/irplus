/**  
   Copyright 2008-2011 University of Rochester

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


package edu.ur.ir.groupspace;

import java.util.List;

import edu.ur.dao.CountableDAO;
import edu.ur.dao.CrudDAO;
import edu.ur.dao.UniqueNameDAO;
import edu.ur.order.OrderType;

/**
 * Data access Interface for dealing with group space information.
 * 
 * @author Nathan Sarr
 *
 */
public interface GroupSpaceDAO extends CrudDAO<GroupSpace>, CountableDAO, UniqueNameDAO<GroupSpace>
{
	/**
	 * Get the list of group spaces.
	 * 
	 * 
	 * @param rowStart - start position
	 * @param numberOfResultsToShow - number of rows to grab.
	 * @param sortType - Order (Desc/Asc) 
	 * 
	 * @return list of groupspaces found.
	 */
	public List<GroupSpace> getGroupspacesNameOrder(int rowStart, int numberOfResultsToShow, OrderType orderType);
}
