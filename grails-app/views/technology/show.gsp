
<%@ page import="com.orangeandbronze.ozmness.Technology" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'technology.label', default: 'Technology')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <sec:access expression="hasRole('ROLE_ADMIN')">
            	<span class="menuButton"><g:link class="edit" action="edit" id="${technologyInstance.id}"><g:message code="default.edit.label" args="[entityName]" /></g:link></span>
            	<span class="menuButton"><g:link class="delete" action="delete" id="${technologyInstance.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">Delete Technology</g:link></span>
            	<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        	</sec:access>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technology.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value"><b>${fieldValue(bean: technologyInstance, field: "name")}</b></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="technology.parent.label" default="Parent" /></td>
                            
                            <td valign="top" class="value"><g:link controller="technology" action="show" id="${technologyInstance?.parent?.id}">${technologyInstance?.parent?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                       	<tr>
                       		<td colspan="10" class="bottomWrapperNoBorders">
					            <div class="buttons">
					                &nbsp;
					            </div>
				            </td>
			            </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
