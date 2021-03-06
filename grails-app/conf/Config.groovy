// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }

}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.orangeandbronze.ozmness.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.orangeandbronze.ozmness.UserRole'
grails.plugins.springsecurity.authority.className = 'com.orangeandbronze.ozmness.Role'
grails.plugins.springsecurity.requestMap.className = 'com.orangeandbronze.ozmness.Requestmap'
grails.plugins.springsecurity.securityConfigType = 'Requestmap'

import grails.plugins.springsecurity.SecurityConfigType

grails.plugins.springsecurity.rejectIfNoRule = false
grails.plugins.springsecurity.securityConfigType = SecurityConfigType.InterceptUrlMap
grails.plugins.springsecurity.interceptUrlMap = [
	
    '/':            							['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/image:**':    							['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/js/**':       							['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/css/**':      							['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/images/**':  	 							['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/login/**':    							['IS_AUTHENTICATED_ANONYMOUSLY'],
    '/logout/**':   							['IS_AUTHENTICATED_ANONYMOUSLY'],
	
	'/employee/create*':   						['ROLE_ADMIN'],
	'/employee/edit/**':						['ROLE_ADMIN'],
	'/employee/changePassword/**':				['ROLE_ADMIN', 'ROLE_DEV'],
	'/employee/delete/**':						['ROLE_ADMIN'],
	'/employee/list*':							['ROLE_ADMIN', 'ROLE_DEV'],
	'/employee/show/**':						['ROLE_ADMIN', 'ROLE_DEV'],
	'/employee/showEmployeeRatings*':			['ROLE_ADMIN', 'ROLE_DEV'],
	'/employee/rateEmployee/**':				['ROLE_DEV'],
	
	'/employeePosition/create*':   				['ROLE_ADMIN'],
	'/employeePosition/edit/**':				['ROLE_ADMIN'],
	'/employeePosition/delete/**':				['ROLE_ADMIN'],
	'/employeePosition/list*':					['ROLE_ADMIN', 'ROLE_DEV'],
	'/employeePosition/show/**':				['ROLE_ADMIN', 'ROLE_DEV'],
	
	'/project/create*':   						['ROLE_ADMIN'],
	'/project/edit/**':							['ROLE_ADMIN', 'ROLE_DEV'],
	'/project/delete/**':						['ROLE_ADMIN'],
	'/project/list*':							['ROLE_ADMIN', 'ROLE_DEV'],
	'/project/show/**':							['ROLE_ADMIN', 'ROLE_DEV'],
	'/project/showPossibleCollaborators*':		['ROLE_ADMIN', 'ROLE_DEV'],
	
	'/rating/create*':   						['ROLE_ADMIN'],
	'/rating/edit/**':							['ROLE_ADMIN', 'ROLE_DEV'],
	'/rating/delete/**':						['ROLE_ADMIN'],
	'/rating/list*':							['ROLE_ADMIN'],
	'/rating/show/**':							['ROLE_ADMIN', 'ROLE_DEV'],
	'/rating/showAvailableEmployeesToBeRated*': ['ROLE_DEV'],
	
	'/technology/create*':   					['ROLE_ADMIN'],
	'/technology/edit/**':						['ROLE_ADMIN'],
	'/technology/delete/**':					['ROLE_ADMIN'],
	'/technology/list*':						['ROLE_ADMIN', 'ROLE_DEV'],
	'/technology/show/**':						['ROLE_ADMIN', 'ROLE_DEV']
	
]
