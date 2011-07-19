
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
            <h1>Rate An Employee</h1>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            
                            <g:sortableColumn property="Name" title="Employee" />
                        
                        	<th>&nbsp;</th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${rateableEmployees}" status="i" var="rateableemployeeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td style="text-align:center;">${fieldValue(bean: rateableemployeeInstance, field: "name")}</td> 
                            
                            <td><g:link action="rateEmployee" controller="employee" id="${rateableemployeeInstance.id}">Rate</g:link></td>
                        
                        </tr>
                    </g:each>
                    	<tr>
                    		<td colspan="10" class="bottomWrapper">
					            <div class="paginateButtons">
					                <g:paginate total="${employeeInstanceTotal}" />
					            </div>
				            </td>
			            </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
