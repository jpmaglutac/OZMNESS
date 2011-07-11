

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
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
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
                                <td valign="top" class="name">
                                    <label for="collaboratorID">Collaborator: </label>
                                </td>
                                
                                <td valign="top">
                                	<g:if test="${possibleCollaborators.size() >0}"> 
                                    	<g:select name="collaboratorID" from="${possibleCollaborators}" optionKey="id" value="${possibleCollaborators?.id}"  noSelection="['null': '']"/>
                                	</g:if>
                                		
                                	<g:else>
                                		No available employees
                                	</g:else>
                                </td>
                                
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="Add Collaborator" class="save" value="${message(code: 'default.button.addCollaborator.label', default: 'Add Collaborator')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
