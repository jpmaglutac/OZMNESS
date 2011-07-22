
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
            <span class="menuButton"><g:link class="show" controller="employee" action="show" id="${params.id}" >${Employee.get(params.id).name}'s Profile</g:link></span>
            <g:if test="${canRate}"><span class="menuButton"><g:link class="edit" action="rateEmployee" id="${params.id}">Rate ${Employee.get(params.id).name}</g:link></span></g:if>
     		<sec:access expression="hasRole('ROLE_ADMIN')">
	            <span class="menuButton"><g:link class="create" controller="employee" action="create">New Employee</g:link></span>
			</sec:access>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /> for ${Employee.get(params.id).name}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                        	<g:set var="raters" value="${ratings[0]}"/>
                        
                            <g:sortableColumn property="technology" title="${message(code: 'rating.technology.label', default: 'Technology')}" />
                        
                        	<g:each in="${raters}" var = "rater">
								<g:sortableColumn property="creator" title="${rater.creator.name}"/>
                        	</g:each>
                            
                            <g:sortableColumn property="average" title="${message(code: 'rating.averageRating.label', default: 'Average Rating')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ratings}" status="i" var="technology">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td style="text-align: center;"><b>${techs[i]}</b></td>
                                               
                            <g:each in="${technology}" var = "rate">
                            
                            	<g:if test="${rate?.rating}">
                            		<td style="text-align: center;"><b>${rate?.rating}</b></td>
                            	</g:if>
                            	<g:else>
                            		<td style="text-align: center;"><b> - </b></td>
                            	</g:else>
                            	
                            </g:each>
                            
                            <td style="text-align: center;"><b>${avgRatings[i]}</b></td>
                                                    
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
