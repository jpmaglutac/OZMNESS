package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
class TechnologyController {
	
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST"]
	
	def scaffold = true

	def save = {
		def technologyInstance = new Technology(params)
		if (technologyInstance.save(flush: true)) {
		flash.message = "Technology created"
		redirect(action: "show", id: technologyInstance.id)
		}
		else {
			render(view: "create", model: [technologyInstance: technologyInstance])
		}
	}
	
    def edit = {
        def technologyInstance = Technology.get(params.id)
        if (!technologyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technology.label', default: 'Technology'), params.id])}"
            redirect(action: "list")
        }
        else {
			if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
				return [technologyInstance: technologyInstance]
			} else {
				flash.message = "You are not authorized to edit technologies!"
				redirect(action: "list")
			}
        }
    }

    def delete = {
        def technologyInstance = Technology.get(params.id)
		if (technologyInstance) {
            try {
				if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
	            	Technology.findAllByParent(technologyInstance).each {
	            		it.parent = null
	            		it.save(flush: true)
	            	}
	            	Project.list().each() {
	            		it.removeFromTechnologies(technologyInstance)
	            	}
	                technologyInstance.delete(flush: true)
	                flash.message = "Technology has been deleted"
	                redirect(action: "list")
				} else {
					flash.message = "You are not authorized to delete technologies!"
					redirect(action: "list")
				}
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'technology.label', default: 'Technology'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technology.label', default: 'Technology'), params.id])}"
            redirect(action: "list")
        }
    }
}
