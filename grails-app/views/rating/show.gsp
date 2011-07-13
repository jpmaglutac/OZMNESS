
<%@ page import="com.orangeandbronze.ozmness.Rating" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rating.label', default: 'Rating')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
        	<sec:access expression="hasRole('ROLE_ADMIN')">
            	<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            	<span class="menuButton"><g:link class="list" controller="employee" action="showEmployeeRatings" id="${ratingInstance.employeeRated.id}" >${ratingInstance.employeeRated}'s Ratings</g:link></span>
        		<span class="menuButton"><g:link class="edit" id="${ratingInstance.id}" action="edit">Edit Rating</g:link></span>
        		<span class="menuButton"><g:link class="delete" action="delete" id="${ratingInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete Rating</g:link></span>
        	</sec:access>
        	<sec:access expression="hasRole('ROLE_DEV')">
            	<span class="menuButton"><g:link class="list" controller="employee" action="list">Employee List</g:link></span>
            	<span class="menuButton"><g:link class="show" controller="employee" action="show" id="${ratingInstance.employeeRated.id}">${ratingInstance.employeeRated}'s Profile</g:link></span>
            	<span class="menuButton"><g:link class="list" controller="employee" action="showEmployeeRatings" id="${ratingInstance.employeeRated.id}" >${ratingInstance.employeeRated}'s Ratings</g:link></span>
        	</sec:access>
        	<g:if test="${loggedInUser == ratingInstance.creator}">
        		<span class="menuButton"><g:link class="edit" id="${ratingInstance.id}" action="edit">Edit Rating</g:link></span>
       		</g:if>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                   		<sec:access expression="hasRole('ROLE_ADMIN')">
                    
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="rating.id.label" default="Id" /></td>
	                            
	                            <td valign="top" class="value">${fieldValue(bean: ratingInstance, field: "id")}</td>
	                            
	                        </tr>
	                        
                        </sec:access>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rating.employeeRated.label" default="Employee Rated" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${ratingInstance?.employeeRated?.id}"><b>${ratingInstance?.employeeRated?.encodeAsHTML()}</b></g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rating.creator.label" default="Creator" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${ratingInstance?.creator?.id}"><b>${ratingInstance?.creator?.encodeAsHTML()}</b></g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rating.value.label" default="Value" /></td>
                            
                            <td valign="top" class="value"><b>${fieldValue(bean: ratingInstance, field: "value")}</b></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rating.technology.label" default="Technology" /></td>
                            
                            <td valign="top" class="value"><g:link controller="technology" action="show" id="${ratingInstance?.technology?.id}">${ratingInstance?.technology?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rating.comment.label" default="Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: ratingInstance, field: "comment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rating.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${ratingInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                    	<tr>
                    		<td colspan="10" class="bottomWrapperNoBorders">
					            <div class="buttons">
					                &nbsp;
					            </div>
				            </td>
			            </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
