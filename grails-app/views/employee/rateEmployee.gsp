

<%@ page import="com.orangeandbronze.ozmness.Rating" %>
<%@ page import="com.orangeandbronze.ozmness.Employee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rating.label', default: 'Rating')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list">Employee List</g:link></span>
            <span class="menuButton"><g:link class="show" controller="employee" action="show" id="${params.id}" >${Employee.get(params.id).name}'s Profile</g:link></span>
        	<span class="menuButton"><g:link class="list" controller="employee" action="showEmployeeRatings" id="${params.id}" >${Employee.get(params.id).name}'s Ratings</g:link></span>
        </div>
        <div class="body">
            <h1>Rate ${Employee.get(params.id).name}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${ratingInstance}">
            <div class="errors">
                <g:renderErrors bean="${ratingInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="saveEmployeeRating" >
                <div class="dialog">
                    <table style="width: 100px">
                        <tbody>
                        
                        	<g:each in="${com.orangeandbronze.ozmness.Technology.list()}" status="i" var="tech">
                        		<tr class="prop">
                        			<td valign="top" class="name" >
                        				<h1>${tech}</h1>
                        			</td>
                        		</tr>
                        		<tr class="prop">
                        			<td valign="top" class="name" >
                                    	<label for="value"><g:message code="rating.rating.label" default="Rating" /></label>:<br />
                                    	<g:radioGroup name="${tech.id}_rating" values="${com.orangeandbronze.ozmness.RatingValue.values()}" labels="${com.orangeandbronze.ozmness.RatingValue.values()*.name}" >
                                    		${it.label}: ${it.radio} &nbsp;&nbsp;
                                    	</g:radioGroup>
                                	</td>
                                	
                                	<td valign="top" class="name" >
                                    	<label for="comment"><g:message code="rating.comment.label" default="Comment" /></label>:<br />
                                    	<g:textArea name="${tech.id}_comment" cols="40" rows="5" />
                                	</td>
                        		</tr>
                        	</g:each>
                        
                            <tr>
		                      	<td colspan="10" class="bottomWrapperNoBorders">
							        <div class="buttons">
							        	<g:hiddenField name="employeeRated.id" value="${employeeId}" />
					                    <span class="button"><g:submitButton name="create" class="save" value="Rate" /></span>
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
