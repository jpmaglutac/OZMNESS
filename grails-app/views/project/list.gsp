
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
                            
                            <th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${projectInstanceList}" status="i" var="projectInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><td style="text-align:center;">${fieldValue(bean: projectInstance, field: "id")}</td></sec:access>
                        
                            <td>${fieldValue(bean: projectInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: projectInstance, field: "lead")}</td>
                            
                            <td>
                            	<g:link action="show" id="${projectInstance.id}">view more</g:link>
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
