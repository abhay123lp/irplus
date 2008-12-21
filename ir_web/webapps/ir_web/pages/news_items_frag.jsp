<!--  
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
-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ir" uri="ir-tags"%>
<%@ taglib prefix="ur" uri="ur-tags"%>


<c:if test="${!ur:isEmpty(newsItems)}">
    <table  class="baseTable">

<c:forEach items="${newsItems}" var="newsItem" >
        <c:url var="newsUrl" value="/viewNews.action">
             <c:param name="newsItemId" value="${newsItem.id}"/>
        </c:url>
        <tr>
            <td class="baseTableImage">
                <ir:transformUrl systemCode="PRIMARY_THUMBNAIL" download="true" irFile="${newsItem.primaryPicture}" var="url"/>
                <c:if test="${url != null}">
                   <a href="${newsUrl}"> <img height="66px" width="100px" src="${url}"/></a>
                </c:if>
                
            </td>
            <td>
                <p><strong><a href="${newsUrl}">${newsItem.name}</a></strong> - ${newsItem.description}</p>
            </td>
        </tr>
        
           
</c:forEach>
    </table>
    <table class="buttonTable">
        <tr>
            <td class="leftButton">
                       <button class="ur_button" 
	                           onmouseover="this.className='ur_buttonover';"
 		                       onmouseout="this.className='ur_button';"
 		                       onclick="javascript:YAHOO.ur.public.home.getNewsItems(${currentLocation}, 'PREV', 2);">&lt; Previous</button>
 		    </td>
 		    <td class="rightButton">
 		               <button class="ur_button" 
	                           onmouseover="this.className='ur_buttonover';"
 		                       onmouseout="this.className='ur_button';"
 		                       onclick="javascript:YAHOO.ur.public.home.getNewsItems(${currentLocation}, 'NEXT', 2);">Next &gt;</button>
             </td>
         </tr>
    </table>
</c:if>
    


<c:if test="${ur:isEmpty(newsItems)}">
    <p>There are currently no news items</p>
</c:if>