
<%@ page import="com.orangeandbronze.ozmness.EmployeePosition" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employeePosition.label', default: 'Employee Position')}" />
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
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><g:sortableColumn property="id" style="text-align:center; vertical-align:middle;" title="${message(code: 'employeePosition.id.label', default: 'ID')}" /></sec:access>
                        
                            <g:sortableColumn style="text-align:center; vertical-align:middle;" property="name" title="${message(code: 'employeePosition.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn style="text-align:center;" property="recommendedRating" title="${message(code: 'employeePosition.recommendedRating.label', default: 'Recommended<br />Rating')}" />
                        
                        	<th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${employeePositionInstanceList}" status="i" var="employeePositionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><td style="text-align:center;">${fieldValue(bean: employeePositionInstance, field: "id")}</td></sec:access>
                        
                            <td>${fieldValue(bean: employeePositionInstance, field: "name")}</td>
                        
                            <td style="text-align:center;">${fieldValue(bean: employeePositionInstance, field: "recommendedRating")}</td>
                            
                            <td>
                            	<g:link action="show" id="${employeePositionInstance.id}">view more</g:link>
                            	<sec:access expression="hasRole('ROLE_ADMIN')"> | <g:link action="edit" id="${employeePositionInstance.id}">edit</g:link></sec:access>
                           	</td>
                        
                        </tr>
                    </g:each>
                        <tr>
                        	<td colspan="10" class="bottomWrapper">
                  	            <div class="paginateButtons">
					                <g:paginate total="${employeePositionInstanceTotal}" />
					            </div>
				            </td>
			            </tr>  
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
