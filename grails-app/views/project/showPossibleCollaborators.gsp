

<%@ page import="com.orangeandbronze.ozmness.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        	<span class="menuButton"><g:link class="show" action="show" id="${params.id}">Project "${projectInstance.name}"</g:link></span>
        	<span class="menuButton"><g:link class="edit" action="edit" id="${projectInstance.id}">Edit Project</g:link></span>
        	<sec:access expression="hasRole('ROLE_ADMIN')">
        		<span class="menuButton"><g:link class="delete" action="delete" id="${projectInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete Project</g:link></span>
        		<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        	</sec:access>
        </div>
        <div class="body">
            <h1>Add Collaborator/s</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${projectInstance}">
            <div class="errors">
                <g:renderErrors bean="${projectInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="addCollaborator" >
                <div class="dialog">
                	<g:hiddenField name="id" value="${projectInstance.id}"/>
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <g:message code="project.name.label" default="Name" />
                                </td>
                                <td valign="top" class="value">
                                    ${fieldValue(bean: projectInstance, field: "name")}
                                </td>
                            </tr>
                            
                            <tr class="prop">
                            
	                            <td valign="top" class="name"><g:message code="project.lead.label" default="Lead" /></td>
	                            
	                            <td valign="top" class="value"><g:link controller="employee" action="show" id="${projectInstance?.lead?.id}">${projectInstance?.lead?.encodeAsHTML()}</g:link></td>
	                            
	                        </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="collaboratorID">Collaborator/s: </label>
                                </td>
                                
                                <td valign="top">
                                	<g:if test="${possibleCollaborators.size() >0}"> 
                                    	<g:select name="collaboratorID" from="${possibleCollaborators}" optionKey="id" value="${possibleCollaborators?.id}"/>
                                	</g:if>
                                		
                                	<g:else>
                                		No available employees
                                	</g:else>
                                </td>
                                
                            </tr>
                        
                        	<tr>
                        		<td colspan="10" class="bottomWrapperNoBorders">
                     		        <div class="buttons">
					                    <span class="button"><g:submitButton name="Add Collaborator" class="save" value="Add Collaborator/s" /></span>
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
