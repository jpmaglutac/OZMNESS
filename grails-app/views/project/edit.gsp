

<%@ page import="com.orangeandbronze.ozmness.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <sec:access expression="hasRole('ROLE_ADMIN')">
            	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        	</sec:access>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${projectInstance}">
            <div class="errors">
                <g:renderErrors bean="${projectInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${projectInstance?.id}" />
                <g:hiddenField name="version" value="${projectInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="project.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${projectInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lead"><g:message code="project.lead.label" default="Lead" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'lead', 'errors')}">
                                    <g:select name="lead.id" from="${com.orangeandbronze.ozmness.Employee.list()}" optionKey="id" value="${projectInstance?.lead?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="technologies"><g:message code="project.technologies.label" default="Technologies" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: projectInstance, field: 'technologies', 'errors')}">
                                    <g:select name="technologies" from="${com.orangeandbronze.ozmness.Technology.list()}" multiple="yes" optionKey="id" size="5" value="${projectInstance?.technologies*.id}" />
                                </td>
                            </tr>
                        
                        	<tr>
                        		<td colspan="10" class="bottomWrapperNoBorders">
					                <div class="buttons">
					                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
					                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
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
