<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />


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

<%@ taglib prefix="ur" uri="ur-tags"%>
<%@ taglib prefix="ir" uri="ir-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--  document type -->
<c:import url="/inc/doctype-frag.jsp"/>
          
<fmt:setBundle basename="messages"/>
<html>

<head>
    <title>Upload Marc File</title>
    
    <!-- Medatadata fragment for page cache -->
    <c:import url="/inc/meta-frag.jsp"/>
    
    <!-- Core + Skin CSS -->
    <ur:styleSheet href="page-resources/yui/reset-fonts-grids/reset-fonts-grids.css"/>
    <ur:styleSheet href="page-resources/css/base-ur.css"/>
    <ur:styleSheet href="page-resources/yui/menu/assets/skins/sam/menu.css"/>
    
    <ur:styleSheet href="page-resources/css/main_menu.css"/>
    <ur:styleSheet href="page-resources/css/global.css"/>
    <ur:styleSheet href="page-resources/css/tables.css"/>
    
    <!-- CSS files --> 
    <ur:js src="page-resources/yui/yahoo-dom-event/yahoo-dom-event.js"/>
    <ur:js src="page-resources/yui/container/container_core-min.js"/>
    <ur:js src="page-resources/yui/menu/menu-min.js"/>

    <ur:js src="page-resources/js/menu/main_menu.js"/>
</head>

<body class="yui-skin-sam">
    <!--  yahoo doc 2 template creates a page 950 pixles wide -->
    <div id="doc2">  

    <!--  this is the header of the page -->
    <c:import url="/inc/header.jsp"/>
            
    <!--  this is the body regin of the page -->
    <div id="bd">
    
        <h3> Import marc file to: <c:if test="${parentCollectionId > 0}">/My Publications${parentCollection.fullPath}</c:if><c:if test="${parentCollectionId <=0 }">My Publications</c:if></h3>
        <c:url var="workspaceUrl" value="/user/workspace.action">
            <c:param name="showCollectionTab" value="true"/>
            <c:param name="parentCollectionId" value="${parentCollectionId}"/>
        </c:url>
        <h3><a href="${workspaceUrl}">Back to workspace</a></h3>
	        <form id="marcImport" name="macImport" method="post" enctype="multipart/form-data" 
	            action="<c:url value="/admin/uploadMarcFile.action"/>">
	            <input type="hidden" name="parentCollectionId" value="${parentCollectionId}"/>
                <table class="formTable">
                    <tr>
                        <td class="label">File:</td>
                        <td class="input"><input type="file" name="file" size="100"/></td>
                    </tr>
                    <tr>
                        <td colspan="2" class="buttons"><input type="submit" value="Upload"/></td>
                    </tr>
	            </table>
	        </form>
      </div>
      <!--  end the body tag --> 

      <!--  this is the footer of the page -->
      <c:import url="/inc/footer.jsp"/>
        
    </div>
    <!-- end doc -->

</body>
</html>