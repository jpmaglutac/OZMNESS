

<%@ page import="com.orangeandbronze.ozmness.Rating" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rating.label', default: 'Rating')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
        	<sec:access expression="hasRole('ROLE_ADMIN')">
            	<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
         	</sec:access>
         	<sec:access expression="hasRole('ROLE_DEV')">
         		<span class="menuButton"><g:link class="list" controller="employee" action="list">Employee List</g:link></span>
         		<span class="menuButton"><g:link class="show" controller="employee" action="show" id="${ratingInstance.employeeRated.id}" >${ratingInstance.employeeRated}'s Profile</g:link></span>
        	</sec:access>
          		<span class="menuButton"><g:link class="list" controller="employee" action="showEmployeeRatings" id="${ratingInstance.employeeRated.id}" >${ratingInstance.employeeRated}'s Ratings</g:link></span>
        		<span class="menuButton"><g:link class="show" id="${ratingInstance.id}" action="show">Show Rating</g:link></span>
          	<sec:access expression="hasRole('ROLE_ADMIN')">
        		<span class="menuButton"><g:link class="delete" action="delete" id="${ratingInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete Rating</g:link></span>
        	</sec:access>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${ratingInstance}">
            <div class="errors">
                <g:renderErrors bean="${ratingInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${ratingInstance?.id}" />
                <g:hiddenField name="version" value="${ratingInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="employeeRated"><g:message code="rating.employeeRated.label" default="Employee Rated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ratingInstance, field: 'employeeRated', 'errors')}">
                                    <b>${ratingInstance?.employeeRated?.username}</b>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creator"><g:message code="rating.creator.label" default="Creator" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ratingInstance, field: 'creator', 'errors')}">
                                    <b>${ratingInstance?.creator?.username}</b>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="value"><g:message code="rating.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="rating ${hasErrors(bean: ratingInstance, field: 'rating', 'errors')}">
                                    <g:radioGroup name="rating" values="${com.orangeandbronze.ozmness.RatingValue.values()}" labels="${com.orangeandbronze.ozmness.RatingValue.values()*.name}" value="${(ratingInstance.rating)?:com.orangeandbronze.ozmness.RatingValue.values()[0]}">
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
					                    <span class="button"><g:actionSubmit class="save" action="update" value="Save Rating" /></span>
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
