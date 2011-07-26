
<%@ page import="com.orangeandbronze.ozmness.Employee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
			<span class="menuButton"><g:link class="list" action="showEmployeeRatings" id="${params.id}" >${Employee.get(params.id).name}'s Ratings</g:link></span>
			<g:if test="${canRate}">
				<span class="menuButton"><g:link class="edit" action="rateEmployee" id="${params.id}">Rate ${Employee.get(params.id).name}</g:link></span>
        	</g:if>
     		<sec:access expression="hasRole('ROLE_ADMIN')">
	            <span class="menuButton"><g:link class="edit" action="edit" id="${employeeInstance.id}">Edit Employee</g:link></span>
	            <span class="menuButton"><g:link class="edit" controller="employee" action="changePassword" id="${params.id}" >Change Password</g:link></span>
	            <span class="menuButton"><g:link class="delete" action="delete" id="${employeeInstance.id}">Delete Employee</g:link></span>
	            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
			</sec:access>
			<g:if test="${loggedInUserId == employeeInstance.id}">
				<span class="menuButton"><g:link class="edit" controller="employee" action="changePassword" id="${params.id}" >Change Password</g:link></span>
	        </g:if>
        </div>
        <div class="body">
            <h1>${employeeInstance.name}'s Profile</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                    	<sec:access expression="hasRole('ROLE_ADMIN')">
                    	
	                        <tr class="prop">
	                            <td valign="top" class="name">ID</td>
	                            
	                            <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "id")}</td>
	                            
	                        </tr>
	                        
                        </sec:access>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value"><b>${employeeInstance.username}</b></td>
                            
                        </tr>
                    
                    	<tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.position.label" default="Position" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employeePosition" action="show" id="${employeeInstance?.position?.id}">${employeeInstance?.position?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <sec:access expression="hasRole('ROLE_ADMIN')">
	                    
	                        <tr class="prop">
	                            <td valign="top" class="name">Status</td>
	                            
	                            <td valign="top" class="value">
	                            	<g:if test="${employeeInstance.enabled}">enabled</g:if><g:else>disabled</g:else>
	                            	<g:if test="${employeeInstance.accountExpired}">
	                            		<br />
	                            		account expired
	                            	</g:if>
	                        	    <g:if test="${employeeInstance.accountLocked}">
	                            		<br />
	                            		account locked
	                            	</g:if>
	                            	<g:if test="${employeeInstance.passwordExpired}">
	                            		<br />
	                            		password expired
	                            	</g:if>
	                            </td>
	                            
	                        </tr>
	                        
                        </sec:access>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.mentor.label" default="Mentor" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${employeeInstance?.mentor?.id}">${employeeInstance?.mentor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.projects.label" default="Projects" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${employeeInstance.projects}" var="p">
                                    <li><g:link controller="project" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
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
