
<%@ page import="com.orangeandbronze.ozmness.Rating" %>
<%@ page import="com.orangeandbronze.ozmness.Employee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rating.label', default: 'Rating')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list">Employee List</g:link></span>
            <span class="menuButton"><g:link class="show" controller="employee" action="show" id="${params.id}" >${Employee.get(params.id)}'s Profile</g:link></span>
            <sec:access expression="hasRole('ROLE_DEV')"><span class="menuButton"><g:link class="edit" action="rateEmployee" id="${params.id}">Rate ${Employee.get(params.id)}</g:link></span></sec:access>
     		<sec:access expression="hasRole('ROLE_ADMIN')">
	            <span class="menuButton"><g:link class="create" controller="employee" action="create">New Employee</g:link></span>
			</sec:access>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /> for ${Employee.get(params.id)}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><g:sortableColumn property="id" title="${message(code: 'rating.id.label', default: 'Id')}" /></sec:access>
                        
                            <g:sortableColumn style="text-align: center;" property="value" title="${message(code: 'rating.rating.label', default: 'Rating')}" />
                        
                            <g:sortableColumn property="technology" title="${message(code: 'rating.technology.label', default: 'Technology')}" />
                        
							<g:sortableColumn property="creator" title="${message(code: 'rating.creator.label', default: 'Creator')}" />
                                                      
                            <g:sortableColumn property="comment" title="${message(code: 'rating.comment.label', default: 'Comment')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'rating.dateCreated.label', default: 'Date Created')}" />
                        
                        	<th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ratings}" status="i" var="ratingInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <sec:access expression="hasRole('ROLE_ADMIN')"><td><g:link controller="rating" action="show" id="${ratingInstance.id}">${fieldValue(bean: ratingInstance, field: "id")}</g:link></td></sec:access>
                        
                            <td style="text-align: center;"><b>${ratingInstance.rating.name}</b></td>
                                               
                            <td>${fieldValue(bean: ratingInstance, field: "technology")}</td>
                        
                            <td>${fieldValue(bean: ratingInstance, field: "creator")}</td>
                            
                            <td>${fieldValue(bean: ratingInstance, field: "comment")}</td>
                        
                            <td><g:formatDate date="${ratingInstance.dateCreated}" /></td>
                            
                            <td style="text-align:center;"><g:link controller="rating" action="show" id="${ratingInstance.id}">view</g:link>
                            	<g:if test="${loggedInUser == ratingInstance.creator}"> | <g:link controller="rating" action="edit" id="${ratingInstance.id}">edit</g:link></g:if>
                            	<sec:access expression="hasRole('ROLE_ADMIN')"> | <g:link controller="rating" action="edit" id="${ratingInstance.id}">edit</g:link> | <g:link controller="rating" action="delete" id="${ratingInstance.id}" params="[employee: params.id]" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">delete</g:link></sec:access>
                           	</td>
                        
                        </tr>
                    </g:each>
                        <tr>
                        	<td colspan="10" class="bottomWrapper">
                        		<div class="paginateButtons"></div>
                      		</td>
                   		</tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
