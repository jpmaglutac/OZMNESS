
<%@ page import="com.orangeandbronze.ozmness.Technology" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'technology.label', default: 'Technology')}" />
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
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><g:sortableColumn style="text-align:center;" property="id" title="${message(code: 'technology.id.label', default: 'ID')}" /></sec:access>
                        
                            <g:sortableColumn property="name" title="${message(code: 'technology.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="parent" title="${message(code: 'technology.parent.label', default: 'Parent')}" />
                            
                            <th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${technologyInstanceList}" status="i" var="technologyInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><td style="text-align:center;">${fieldValue(bean: technologyInstance, field: "id")}</td></sec:access>
                        
                            <td>${fieldValue(bean: technologyInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: technologyInstance, field: "parent")}</td>
                            
                            <td>
                            	<g:link action="show" id="${technologyInstance.id}">view more</g:link>
                            	<sec:access expression="hasRole('ROLE_ADMIN')"> | <g:link action="edit" id="${technologyInstance.id}">edit</g:link></sec:access>
                           	</td>
                        
                        </tr>
                    </g:each>
                        <tr>
                    		<td colspan="10" class="bottomWrapper">
					            <div class="paginateButtons">
					                <g:paginate total="${technologyInstanceTotal}" />
					            </div>
            				</td>
          				</tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
