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

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ur" uri="ur-tags"%>
<%@ taglib prefix="ir" uri="ir-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--  document type -->
<c:import url="/inc/doctype-frag.jsp"/>
          
<fmt:setBundle basename="messages"/>
<html>

<head>
    <title>Content Types</title>
    
    <!-- Medatadata fragment for page cache -->
    <c:import url="/inc/meta-frag.jsp"/>
    
    <!-- Core + Skin CSS -->
    <ur:styleSheet href="page-resources/yui/reset-fonts-grids/reset-fonts-grids.css"/>
	<ur:styleSheet href="page-resources/yui/assets/skins/sam/skin.css"/>
    <ur:styleSheet href="page-resources/css/base-ur.css"/>

    <ur:styleSheet href="page-resources/css/main_menu.css"/>
    <ur:styleSheet href="page-resources/css/global.css"/>
    <ur:styleSheet href="page-resources/css/tables.css"/>
    
    <ur:js src="page-resources/yui/utilities/utilities.js"/>
    <ur:js src="page-resources/yui/button/button-min.js"/>
    <ur:js src="page-resources/yui/container/container-min.js"/>
 	<ur:js src="page-resources/yui/menu/menu-min.js"/>
 	
 	<ur:js src="pages/js/base_path.js"/>
 	<ur:js src="page-resources/js/util/ur_util.js"/>
 	<ur:js src="page-resources/js/menu/main_menu.js"/>
    <ur:js src="pages/js/ur_table.js"/>
    <ur:js src="page-resources/js/admin/content_type.js"/>
</head>

<body class="yui-skin-sam">

    <!--  yahoo doc 2 template creates a page 950 pixels wide -->
    <div id="doc2">
      
        <!--  this is the header of the page -->
        <c:import url="/inc/header.jsp"/>
      
        <h3>Edit Content Types</h3>
  
        <div id="bd">
      
	        <table>
	            <tr>
	                <td>
		                <button id="showContentType" class="ur_button" 
 		                               onmouseover="this.className='ur_buttonover';"
 		                               onmouseout="this.className='ur_button';">New Content type</button> 
	                </td>
	                <td>
	                    <button id="showDeleteContentType" class="ur_button" 
 		                               onmouseover="this.className='ur_buttonover';"
 		                               onmouseout="this.className='ur_button';">Delete</button>
	                </td>
	            </tr>
	        </table>
	        
	        <ur:div id="newContentTypes"></ur:div>
	      

	        
      </div>
      <!--  end body div -->
      
      <!--  this is the footer of the page -->
      <c:import url="/inc/footer.jsp"/>
  
  </div>
  <!--  End doc div-->
  
  <ur:div id="newContentTypeDialog" cssClass="hidden">
    <ur:div cssClass="hd">Content Type Information</ur:div>
    <ur:div cssClass="bd">
      <ur:basicForm id="addContentType" name="newContentTypeForm" 
		                    method="post" 
		                    action="admin/createContentType.action">
	    <ur:div id="newContentTypeDialogFields">
	      <%@ include file="/pages/admin/item/metadata/content_types/content_type_form.jsp" %>
	    </ur:div>
	  </ur:basicForm>
    </ur:div>
  </ur:div>
	         
  <ur:div id="deleteContentTypeDialog" cssClass="hidden">
    <ur:div cssClass="hd">Delete Content Types</ur:div>
	  <ur:div cssClass="bd">
	    <ur:basicForm id="deleteContentType" name="deleteContentType" method="post" 
		                action="user/deleteContentType.action">
		 <ur:div id="deleteContentTypeError" cssClass="errorMessage"></ur:div>
		   <p>Are you sure you wish to delete the selected content types?</p>
        </ur:basicForm>
      </ur:div>
  </ur:div>
</body>
</html>