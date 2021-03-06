

<%@ page import="com.orangeandbronze.ozmness.Employee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${employeeInstance}">
            <div class="errors">
                <g:renderErrors bean="${employeeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username"><g:message code="employee.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" value="${employeeInstance?.username}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="employee.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${employeeInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="employee.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" value="${employeeInstance?.password}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="retypePassword">Retype Password</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="retypePassword" value="" />
                                </td>
                            </tr>                            
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="position"><g:message code="employee.position.label" default="Position" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'position', 'errors')}">
                                    <g:select name="position.id" from="${com.orangeandbronze.ozmness.EmployeePosition.list()}" optionKey="id" value="${employeeInstance?.position?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled"><g:message code="employee.enabled.label" default="Enabled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${employeeInstance?.enabled}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="mentor"><g:message code="employee.mentor.label" default="Mentor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'mentor', 'errors')}">
                                    <g:select name="mentor.id" from="${com.orangeandbronze.ozmness.Employee.list()}" optionKey="id" value="${employeeInstance?.mentor?.id}"  noSelection="['null': '']"/>
                                </td>
                            </tr>
                                       
	                      	<tr>
		                      	<td colspan="10" class="bottomWrapperNoBorders">
		                      		<input type="hidden" name="accountExpired" value="false" />
		                      		<input type="hidden" name="passwordExpired" value="false" />
		                      		<input type="hidden" name="accountLocked" value="false" />
                   	                <div class="buttons">
					                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
					                </div>
				                </td>
                        	</tr>
                        </tbody>
                    </table>
                </div>
            </g:form>
        </div>
    </body>
</html>
