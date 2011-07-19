
<%@ page import="com.orangeandbronze.ozmness.EmployeePosition" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employeePosition.label', default: 'Employee Position')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <sec:access expression="hasRole('ROLE_ADMIN')">
        		<span class="menuButton"><g:link class="edit" action="edit" id="${employeePositionInstance.id}">Edit Employee Position</g:link></span>
        		<span class="menuButton"><g:link class="delete" action="delete" id="${employeePositionInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete Employee Position</g:link></span>
            	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        	</sec:access>
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
	                            <td valign="top" class="name"><g:message code="employeePosition.id.label" default="ID" /></td>
	                            
	                            <td valign="top" class="value">${fieldValue(bean: employeePositionInstance, field: "id")}</td>
	                            
	                        </tr>
                        </sec:access>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeePosition.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeePositionInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employeePosition.recommendedRating.label" default="Recommended Rating" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeePositionInstance, field: "recommendedRating")}</td>
                            
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
