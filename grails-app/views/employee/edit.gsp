

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
           	<span class="menuButton"><g:link class="edit" controller="employee" action="changePassword" id="${params.id}" >Change Password</g:link></span>
           	<span class="menuButton"><g:link class="delete" action="delete" id="${employeeInstance.id}">Delete Employee</g:link></span>
           	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1>Edit ${employeeInstance.name}</h1>
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
                                    <g:textField name="name" value="${employeeInstance?.name}"/>
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
                                  <label for="mentor"><g:message code="employee.mentor.label" default="Mentor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'mentor', 'errors')}">
                                    <g:select name="mentor.id" from="${possibleMentors}" optionKey="id" value="${employeeInstance?.mentor?.id}"  noSelection="['null': '']"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="projects"><g:message code="employee.projects.label" default="Projects" /></label>
                                  </td>
                                <td valign="top" class="value ${hasErrors(bean: employeeInstance, field: 'projects', 'errors')}">
						                                    
									<ul>
									<g:each in="${employeeInstance?.projects?}" var="p">
									    <li><g:link controller="project" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
									</g:each>
									</ul>
									<br />
                                  	<g:link controller="project" action="create" params="['employee.id': employeeInstance?.id]">new project</g:link>
                                	<br />
                                	<br />								
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
                                                               
	                      	<tr>
		                      	<td colspan="10" class="bottomWrapperNoBorders">
	                             	<div class="buttons">
	                             		<span class="button"><g:actionSubmit class="save" action="update" value="Save Employee" /></span>
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
