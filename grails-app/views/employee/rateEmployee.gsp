

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
            <span class="menuButton"><g:link class="show" controller="employee" action="show" id="${params.id}" >${Employee.get(params.id)}'s Profile</g:link></span>
        	<span class="menuButton"><g:link class="list" controller="employee" action="showEmployeeRatings" id="${params.id}" >${Employee.get(params.id)}'s Ratings</g:link></span>
        </div>
        <div class="body">
            <h1>Rate ${Employee.get(params.id)}</h1>
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
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value"><g:message code="rating.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="rating ${hasErrors(bean: ratingInstance, field: 'rating', 'errors')}">
                                    <g:radioGroup name="rating" values="${com.orangeandbronze.ozmness.RatingValue.values()}" labels="${com.orangeandbronze.ozmness.RatingValue.values()*.name}" value="${com.orangeandbronze.ozmness.RatingValue.values()[0]}">
                                    	${it.radio} ${it.label}<br />
                                    </g:radioGroup>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="comment"><g:message code="rating.comment.label" default="Comment" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ratingInstance, field: 'comment', 'errors')}">
                                    <g:textArea name="comment" cols="40" rows="5" value="${ratingInstance?.comment}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="technology"><g:message code="rating.technology.label" default="Technology" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ratingInstance, field: 'technology', 'errors')}">
                                    <g:select name="technology.id" from="${com.orangeandbronze.ozmness.Technology.list()}" optionKey="id" value="${ratingInstance?.technology?.id}"  />
                                </td>
                            </tr>
                            
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
