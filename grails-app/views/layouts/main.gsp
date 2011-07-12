<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="OZMNESS" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favico.ico')}" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none; margin:auto;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
        </div>
        <div class="loginNav">
          	<sec:ifNotLoggedIn>
    		<a class="log" href="${createLink(uri: '/login/index')}">login</a>
			</sec:ifNotLoggedIn>
    		<sec:ifLoggedIn>
    		<sec:loggedInUserInfo field="username"/> | 
    		<a class="log" href="${createLink(uri: '/logout/index')}">logout</a>
			</sec:ifLoggedIn>
		</div>
        <div id="mainLogo">
       		<img src="${resource(dir:'images',file:'mainlogo.png')}" alt="logo" style="position:relative; z-index:-100;" />
     	</div>
    	<div class="mainNav">
    		<span class="menuButton"><a class="main" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
    		<sec:ifAnyGranted roles="ROLE_ADMIN, ROLE_DEV">
				<span class="menuButton"><a class="main" href="${createLink(uri: '/employee')}">Employees</a></span>
				<span class="menuButton"><a class="main" href="${createLink(uri: '/project')}">Projects</a></span>
				<sec:access expression="hasRole('ROLE_ADMIN')"><span class="menuButton"><a class="main" href="${createLink(uri: '/rating')}">Ratings</a></span></sec:access>
				<span class="menuButton"><a class="main" href="${createLink(uri: '/employeePosition')}">Employee Positions</a></span>
				<span class="menuButton"><a class="main" href="${createLink(uri: '/technology')}">Technologies</a></span>
    		</sec:ifAnyGranted>
    	</div>
        <g:layoutBody />
    </body>
</html>