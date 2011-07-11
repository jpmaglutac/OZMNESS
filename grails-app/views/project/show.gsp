
<%@ page import="com.orangeandbronze.ozmness.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
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
                            <td valign="top" class="name"><g:message code="project.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="project.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: projectInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="project.lead.label" default="Lead" /></td>
                            
                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${projectInstance?.lead?.id}">${projectInstance?.lead?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="project.technologies.label" default="Technologies" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${projectInstance.technologies}" var="t">
                                    <li><g:link controller="technology" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="project.collaborators.label" default="Collaborators" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${projectInstance.collaborators}" var="c">
                                    <li>
                                    	<g:link controller="employee" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link>
                                    	<g:if test="${loggedInUser == projectInstance.lead}">
                                    		[<g:link action="removeCollaborator" id="${c.id}" params="[projectId: projectInstance.id]" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">x</g:link>]
                                    	</g:if>
                                    	<sec:access expression="hasRole('ROLE_ADMIN')">
                                    		[<g:link action="removeCollaborator" id="${c.id}" params="[projectId: projectInstance.id]" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">x</g:link>]
                                    	</sec:access>
                                    </li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${projectInstance?.id}" />
                    <g:if test="${loggedInUser == projectInstance.lead}">
                    	<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    	<span class="button"><g:actionSubmit action="showPossibleCollaborators" value="${message(code: 'default.button.showPossibleCollaborators.label', default: 'Add Collaborator')}" /></span>
					</g:if>
					<sec:access expression="hasRole('ROLE_ADMIN')">
						<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    	<span class="button"><g:actionSubmit action="showPossibleCollaborators" value="${message(code: 'default.button.showPossibleCollaborators.label', default: 'Add Collaborator')}" /></span>
						<span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
					</sec:access>
                </g:form>
            </div>
        </div>
    </body>
</html>
