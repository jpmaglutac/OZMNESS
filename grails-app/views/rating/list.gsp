
<%@ page import="com.orangeandbronze.ozmness.Rating" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rating.label', default: 'Rating')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">&nbsp;</div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
            
            	<g:if test="${ratingInstanceList.size() > 0}">
	                <table>
	                    <thead>
	                        <tr>
	                        
	                            <g:sortableColumn style="text-align:center;" property="id" title="${message(code: 'rating.id.label', default: 'ID')}" />
	                            
	                            <g:sortableColumn property="employeeRated" title="Employee Rated" />
	                        
	                            <g:sortableColumn property="value" title="${message(code: 'rating.rating.label', default: 'Rating')}" />
	                                                    
	                            <g:sortableColumn property="technology" title="${message(code: 'rating.technology.label', default: 'Technology')}" />
	                        
	                            <g:sortableColumn property="creator" title="${message(code: 'rating.creator.label', default: 'Creator')}" />
	                        
	                            <g:sortableColumn property="comment" title="${message(code: 'rating.comment.label', default: 'Comment')}" />
	                        
	                            <g:sortableColumn property="dateCreated" title="${message(code: 'rating.dateCreated.label', default: 'Date Created')}" />
	                        
	                        	<th>&nbsp;</th>
	                        
	                        </tr>
	                    </thead>
	                    <tbody>
	                    <g:each in="${ratingInstanceList}" status="i" var="ratingInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        
	                            <td style="text-align:center;">${fieldValue(bean: ratingInstance, field: "id")}</td>  
	                            
	                            <td>${ratingInstance.employeeRated}</td>                                            
	                            
	                            <td>${ratingInstance.rating.name}</td>
	                            
	                            <td>${fieldValue(bean: ratingInstance, field: "technology")}</td>
	                        
	                            <td>${ratingInstance.creator.name}</td>
	                        
	                            <td>${fieldValue(bean: ratingInstance, field: "comment")}</td>
	                        
	                            <td><g:formatDate date="${ratingInstance.dateCreated}" /></td>
	                            
	                            <td><g:link action="show" id="${ratingInstance.id}">view</g:link> | <g:link action="edit" id="${ratingInstance.id}">edit</g:link> | <g:link action="delete" id="${ratingInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">delete</g:link></td>
	                        
	                        </tr>
	                    </g:each>
	                    	<tr>
	                    		<td colspan="10" class="bottomWrapper">
						            <div class="paginateButtons">
						                <g:paginate total="${ratingInstanceTotal}" />
						            </div>
					            </td>
				            </tr>
	                    </tbody>
	                </table>
	           	</g:if>
                
                <g:else>
                 	No available Ratings
                </g:else>
            </div>
        </div>
    </body>
</html>
