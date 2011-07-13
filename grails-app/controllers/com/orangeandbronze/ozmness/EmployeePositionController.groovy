package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
class EmployeePositionController {
	
	def springSecurityService

    static allowedMethods = [save: "POST", update: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [employeePositionInstanceList: EmployeePosition.list(params), employeePositionInstanceTotal: EmployeePosition.count()]
    }

    def create = {
        def employeePositionInstance = new EmployeePosition()
        employeePositionInstance.properties = params
        return [employeePositionInstance: employeePositionInstance]
    }

    def save = {
        def employeePositionInstance = new EmployeePosition(params)
        if (employeePositionInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), '\"' + employeePositionInstance.name + '\" (id: ' + employeePositionInstance.id + ')'])}"
            redirect(action: "show", id: employeePositionInstance.id)
        }
        else {
            render(view: "create", model: [employeePositionInstance: employeePositionInstance])
        }
    }

    def show = {
        def employeePositionInstance = EmployeePosition.get(params.id)
        if (!employeePositionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), params.id])}"
            redirect(action: "list")
        }
        else {
            [employeePositionInstance: employeePositionInstance]
        }
    }

    def edit = {
        def employeePositionInstance = EmployeePosition.get(params.id)
		if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
	        if (!employeePositionInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), params.id])}"
	            redirect(action: "list")
	        }
	        else {
	            return [employeePositionInstance: employeePositionInstance]
	        }
		} else {
			flash.message = "You are not authorized to edit employee positions!"
			redirect(action: "list")
		}
    }

    def update = {
        def employeePositionInstance = EmployeePosition.get(params.id)
        if (employeePositionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (employeePositionInstance.version > version) {
                    
                    employeePositionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'employeePosition.label', default: 'Employee Position')] as Object[], "Another user has updated Employee Position \"" + employeePositionInstance.name + '\" (id: ' + employeePositionInstance.id + ") while you were editing")
                    render(view: "edit", model: [employeePositionInstance: employeePositionInstance])
                    return
                }
            }
            employeePositionInstance.properties = params
            if (!employeePositionInstance.hasErrors() && employeePositionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), '\"' + employeePositionInstance.name + '\" (id: ' + employeePositionInstance.id + ')'])}"
                redirect(action: "show", id: employeePositionInstance.id)
            }
            else {
                render(view: "edit", model: [employeePositionInstance: employeePositionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def employeePositionInstance = EmployeePosition.get(params.id)
		if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
	        if (employeePositionInstance) {
	            try {
	                employeePositionInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), params.id])}"
	                redirect(action: "list")
	            }
	            catch (org.springframework.dao.DataIntegrityViolationException e) {
	                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), params.id])}"
	                redirect(action: "show", id: params.id)
	            }
	        }
	        else {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'Employee Position'), params.id])}"
	            redirect(action: "list")
	        }
		} else {
			flash.message = "You are not authorized to delete employee positions!"
			redirect(action: "list")
		}
    }
}
