

<%@ page import="com.orangeandbronze.ozmness.EmployeePosition" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'employeePosition.label', default: 'Employee Position')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
             <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${employeePositionInstance}">
            <div class="errors">
                <g:renderErrors bean="${employeePositionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="employeePosition.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeePositionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${employeePositionInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="recommendedRating"><g:message code="employeePosition.recommendedRating.label" default="Recommended Rating" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: employeePositionInstance, field: 'recommendedRating', 'errors')}">
                                    <g:textField name="recommendedRating" value="${fieldValue(bean: employeePositionInstance, field: 'recommendedRating')}" />
                                </td>
                            </tr>
                            
	                      	<tr>
		                      	<td colspan="10" class="bottomWrapperNoBorders">
                        			<div class="buttons">
					                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
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
