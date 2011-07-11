
<%@ page import="com.orangeandbronze.ozmness.Employee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employee.label', default: 'Employee')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'employee.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'employee.username.label', default: 'Username')}" />
                            
                            <g:sortableColumn property="name" title="${message(code: 'employee.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="position" title="${message(code: 'employee.username.position', default: 'Position')}" />
                            
                            <g:sortableColumn property="mentor" title="${message(code: 'employee.username.mentor', default: 'Mentor')}" />
                            
                            <th>Employee Status</th>
                            
                            <th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${employeeInstanceList}" status="i" var="employeeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: employeeInstance, field: "id")}</td>
                        
                            <td>${fieldValue(bean: employeeInstance, field: "username")}</td>
                            
                            <td>${fieldValue(bean: employeeInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: employeeInstance, field: "position")}</td>
                            
                            <td>${employeeInstance.mentor}</td>
                            
                            <td>
                            	<g:if test="${employeeInstance.enabled}">enabled</g:if><g:else>disabled</g:else>
                            	<g:if test="${employeeInstance.accountExpired}">
                            		<br />
                            		account expired
                            	</g:if>
                        	    <g:if test="${employeeInstance.accountLocked}">
                            		<br />
                            		account locked
                            	</g:if>
                            	<g:if test="${employeeInstance.passwordExpired}">
                            		<br />
                            		password expired
                            	</g:if>
                           	</td>
                            
                            <td><g:link action="show" id="${employeeInstance.id}">view more</g:link></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${employeeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
