
<%@ page import="com.orangeandbronze.ozmness.Project" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'project.label', default: 'Project')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <sec:access expression="hasRole('ROLE_ADMIN')">
            	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        	</sec:access>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><g:sortableColumn style="text-align:center;" property="id" title="${message(code: 'project.id.label', default: 'ID')}" /></sec:access>
                        
                            <g:sortableColumn property="name" title="${message(code: 'project.name.label', default: 'Name')}" />
                                                
                            <th><g:message code="project.lead.label" default="Lead" /></th>
                            
                        	<th>Technologies</th>
                        	
                        	<th>Team Size</th>
                        	
                            <th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${projectInstanceList}" status="i" var="projectInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><td style="text-align:center; vertical-align:middle;">${fieldValue(bean: projectInstance, field: "id")}</td></sec:access>
                        
                            <td style="vertical-align:middle;">${fieldValue(bean: projectInstance, field: "name")}</td>
                        
                            <td style="vertical-align:middle;">${fieldValue(bean: projectInstance, field: "lead")}</td>
                            
                            <td style="vertical-align:middle;">
                            	<ul style="list-style:none; padding: 0">
	                            	<g:each in="${projectInstance.technologies}" status="j" var="technology">
	                            		<li>${technology}</li>
	                            	</g:each>
                            	</ul>
                            </td>
                            
                            <td style="text-align:center; vertical-align:middle;">${projectInstance.collaborators.size() + 1}</td>
                            
                            <td style="text-align:center; vertical-align:middle;">
                            	<g:link action="show" id="${projectInstance.id}" style="line-height:1.5em;">view more</g:link>
                            	<sec:access expression="hasRole('ROLE_ADMIN')"> | <g:link class="delete" action="delete" id="${projectInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">delete</g:link></sec:access>
                            	<g:if test="${loggedInUser == projectInstance.lead} || hasRole('ROLE_ADMIN')">
                            		<br />
                            		<g:link action="edit" id="${projectInstance.id}">edit project</g:link>
                            		<br />
                            		<g:link action="showPossibleCollaborators" id="${projectInstance.id}">add collaborators</g:link>
                           		</g:if>
                           	</td>
                        
                        </tr>
                    </g:each>
                    	<tr>
                    		<td colspan="10" class="bottomWrapper">
					            <div class="paginateButtons">
					                <g:paginate total="${projectInstanceTotal}" />
					            </div>                    		
                    		</td>
                   		</tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
