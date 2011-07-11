
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
			<sec:access expression="hasRole('ROLE_ADMIN')">
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
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: employeeInstance, field: "username")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="employee.position.label" default="Position" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employeePosition" action="show" id="${employeeInstance?.position?.id}">${employeeInstance?.position?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                        
                        <sec:access expression="hasRole('ROLE_ADMIN')">
                    
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="employee.accountExpired.label" default="Account Expired" /></td>
	                            
	                            <td valign="top" class="value"><g:formatBoolean boolean="${employeeInstance?.accountExpired}" /></td>
	                            
	                        </tr>
	                    
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="employee.accountLocked.label" default="Account Locked" /></td>
	                            
	                            <td valign="top" class="value"><g:formatBoolean boolean="${employeeInstance?.accountLocked}" /></td>
	                            
	                        </tr>
	                    
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="employee.enabled.label" default="Enabled" /></td>
	                            
	                            <td valign="top" class="value"><g:formatBoolean boolean="${employeeInstance?.enabled}" /></td>
	                            
	                        </tr>
                    
	                        <tr class="prop">
	                            <td valign="top" class="name"><g:message code="employee.passwordExpired.label" default="Password Expired" /></td>
	                            
	                            <td valign="top" class="value"><g:formatBoolean boolean="${employeeInstance?.passwordExpired}" /></td>
	                            
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
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${employeeInstance?.id}" />
                    <sec:access expression="hasRole('ROLE_ADMIN')">
                    	<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    	<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                    </sec:access>
                    <span class="button"><g:actionSubmit class="list" action="rateEmployee" value="${message(code: 'default.button.rateEmployee.label', default: 'Rate Employee')}" /></span>
                    <span class="button"><g:actionSubmit class="list" action="showEmployeeRatings" value="${message(code: 'default.button.showEmployeeRatings.label', default: 'Show Ratings')}" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
