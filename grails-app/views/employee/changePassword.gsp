

<%@ page import="com.orangeandbronze.ozmness.Employee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
           	<span class="menuButton"><g:link class="show" controller="employee" action="show" id="${params.id}" >${Employee.get(params.id).name}'s Profile</g:link></span>
           	<span class="menuButton"><g:link class="edit" controller="employee" action="edit" id="${params.id}" >Edit Employee</g:link></span>
           	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Change Password for ${employeeInstance.name}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${employeeInstance}">
            <div class="errors">
                <g:renderErrors bean="${employeeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${employeeInstance?.id}" />
                <g:hiddenField name="version" value="${employeeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="password"><g:message code="employee.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" value="" />
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
                                       
	                      	<tr>
		                      	<td colspan="10" class="bottomWrapperNoBorders">
	                             	<div class="buttons">
	                             		<span class="button"><g:actionSubmit class="save" action="updatePassword" value="Change Password" /></span>
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
