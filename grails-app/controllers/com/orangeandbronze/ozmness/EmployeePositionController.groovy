package com.orangeandbronze.ozmness

class EmployeePositionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), employeePositionInstance.id])}"
            redirect(action: "show", id: employeePositionInstance.id)
        }
        else {
            render(view: "create", model: [employeePositionInstance: employeePositionInstance])
        }
    }

    def show = {
        def employeePositionInstance = EmployeePosition.get(params.id)
        if (!employeePositionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), params.id])}"
            redirect(action: "list")
        }
        else {
            [employeePositionInstance: employeePositionInstance]
        }
    }

    def edit = {
        def employeePositionInstance = EmployeePosition.get(params.id)
        if (!employeePositionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [employeePositionInstance: employeePositionInstance]
        }
    }

    def update = {
        def employeePositionInstance = EmployeePosition.get(params.id)
        if (employeePositionInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (employeePositionInstance.version > version) {
                    
                    employeePositionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'employeePosition.label', default: 'EmployeePosition')] as Object[], "Another user has updated this EmployeePosition while you were editing")
                    render(view: "edit", model: [employeePositionInstance: employeePositionInstance])
                    return
                }
            }
            employeePositionInstance.properties = params
            if (!employeePositionInstance.hasErrors() && employeePositionInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), employeePositionInstance.id])}"
                redirect(action: "show", id: employeePositionInstance.id)
            }
            else {
                render(view: "edit", model: [employeePositionInstance: employeePositionInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def employeePositionInstance = EmployeePosition.get(params.id)
        if (employeePositionInstance) {
            try {
                employeePositionInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employeePosition.label', default: 'EmployeePosition'), params.id])}"
            redirect(action: "list")
        }
    }
}
