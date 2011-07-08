package com.orangeandbronze.ozmness

class EmployeeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [employeeInstanceList: Employee.list(params), employeeInstanceTotal: Employee.count()]
    }

    def create = {
        def employeeInstance = new Employee()
        employeeInstance.properties = params
        return [employeeInstance: employeeInstance]
    }

    def save = {
        def employeeInstance = new Employee(params)
		params.password = springSecurityService.encodePassword(params.password)
        if (employeeInstance.save(flush: true)) {
			UserRole.create(employeeInstance, Role.findByAuthority("ROLE_DEV"))
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'employee.label', default: 'Employee'), employeeInstance.id])}"
            redirect(action: "show", id: employeeInstance.id)
        }
        else {
            render(view: "create", model: [employeeInstance: employeeInstance])
        }
    }

    def show = {
        def employeeInstance = Employee.get(params.id)
        if (!employeeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "list")
        }
        else {
            [employeeInstance: employeeInstance]
        }
    }

    def edit = {
        def employeeInstance = Employee.get(params.id)
        if (!employeeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [employeeInstance: employeeInstance]
        }
    }

    def update = {
        def employeeInstance = Employee.get(params.id)
        if (employeeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (employeeInstance.version > version) {
                    
                    employeeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'employee.label', default: 'Employee')] as Object[], "Another user has updated this Employee while you were editing")
                    render(view: "edit", model: [employeeInstance: employeeInstance])
                    return
                }
            }
            employeeInstance.properties = params
            if (!employeeInstance.hasErrors() && employeeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'employee.label', default: 'Employee'), employeeInstance.id])}"
                redirect(action: "show", id: employeeInstance.id)
            }
            else {
                render(view: "edit", model: [employeeInstance: employeeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def employeeInstance = Employee.get(params.id)
        if (employeeInstance) {
            try {
                employeeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "list")
        }
    }
}
