package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
class EmployeeController {
	
	def springSecurityService
	def ratingService

    static allowedMethods = [save: "POST", update: "POST"]

	def scaffold = true
	
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def canRateArray = []
		if(SpringSecurityUtils.ifAllGranted("ROLE_DEV")) {
			Employee.list(params).each() {
			def int temp = it.id
			canRateArray[temp] = ratingService.canRateEmployee(Employee.get(springSecurityService.principal.id), it)
			}
		}
        [employeeInstanceList: Employee.list(params), employeeInstanceTotal: Employee.count(), canRate: canRateArray]
    }
	
    def show = {
        def employeeInstance = Employee.get(params.id)
		def canRate = false
        if (!employeeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "list")
        }
        else {
			if(SpringSecurityUtils.ifAllGranted("ROLE_DEV")) {
				canRate = ratingService.canRateEmployee(Employee.get(springSecurityService.principal.id), Employee.get(params.id))
			}
            [employeeInstance: employeeInstance, canRate: canRate]
        }
    }

    def save = {
		if(params.password == params.retypePassword) {
			params.password = springSecurityService.encodePassword(params.password)
	        def employeeInstance = new Employee(params)		
			if (employeeInstance.save(flush: true)) {
				UserRole.create(employeeInstance, Role.findByAuthority("ROLE_DEV"))
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'employee.label', default: 'Employee'), '\"' + employeeInstance.name + '\"'])}"
	            redirect(action: "show", id: employeeInstance.id)
	        }
	        else {
	            render(view: "create", model: [employeeInstance: employeeInstance])
	        }
		}
		else {
			flash.message="Passwords do not match. Please try again."
			redirect(action: "create", params: [username: params.username, name: params.name])
		}
    }


	def edit = {
		def employeeInstance = Employee.get(params.id)
		if (!employeeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
			redirect(action: "list")
		}
		else {
			if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
				def possibleMentors = Employee.list()
				if(!possibleMentors.isEmpty()){
					possibleMentors.minus(employeeInstance)
				}
				return [employeeInstance: employeeInstance, possibleMentors: possibleMentors]
			
			} else {
				flash.message = "You are not authorized to edit employees!"
				redirect(action: "list")
			}
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
			params.password = employeeInstance.password
            employeeInstance.properties = params
            if (!employeeInstance.hasErrors() && employeeInstance.save(flush: true)) {
                flash.message = employeeInstance.name + " updated"
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
				if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
					employeeInstance.enabled = false
					employeeInstance.save(flush: true)
					flash.message = "Employee's account has been disabled."
					redirect(action: "list")
				} else {
					flash.message = "You are not authorized to delete employees!"
					redirect(action: "list")
				}
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		} else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def changePassword = {
		def employeeInstance = Employee.get(params.id)
		if (!employeeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
			redirect(action: "list")
		}
		else
			return [employeeInstance: employeeInstance]		
	}
	
	def updatePassword = {
		def employeeInstance = Employee.get(params.id)
		if (employeeInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (employeeInstance.version > version) {
					employeeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'employee.label', default: 'Employee')] as Object[], "Another user has updated " + employeeInstance.name + "'s password while you were editing")
					render(view: "changePassword", model: [employeeInstance: employeeInstance])
					return
				}
			}
			if(params.password == params.retypePassword) {
				params.password = springSecurityService.encodePassword(params.password)
				employeeInstance.properties = params
				if (!employeeInstance.hasErrors() && employeeInstance.save(flush: true)) {
					flash.message = "Password for " + employeeInstance.name + " has been updated"
					redirect(action: "show", id: employeeInstance.id)
				}
				else {
					render(view: "changePassword", model: [employeeInstance: employeeInstance])
				}
			} else {
				flash.message="Passwords do not match. Please try again."
				redirect(action: "changePassword", id: employeeInstance.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def showEmployeeRatings = {
		def employeeInstance = Employee.get(params.id)
		def canRate = false
		def loggedInUser = Employee.get(springSecurityService.principal.id)
		if(employeeInstance){
			def listOfRatings = ratingService.separateRatingsPerCreator(params.id)
			def ratings = listOfRatings.allRatings
			def avgRatings = listOfRatings.avgRatings
			def techs = listOfRatings.techs
			if(SpringSecurityUtils.ifAllGranted("ROLE_DEV")) {
				canRate = ratingService.canRateEmployee(Employee.get(springSecurityService.principal.id), Employee.get(params.id))
			}
			println listOfRatings.allRatings
			return [loggedInUser: loggedInUser, ratings: ratings, avgRatings: avgRatings, canRate: canRate, techs: techs]
		}
	}
	
	def rateEmployee = {
		if(!ratingService.canRateEmployee(Employee.get(springSecurityService.principal.id), Employee.get(params.id))){
			flash.message = "You are not allowed to rate this employee!"
			redirect(action: "show", id: params.id)
			return
		}
		[employeeId: params.id]
	}
	
	def saveEmployeeRating = {
		ratingService.saveRatingForm(Employee.get(springSecurityService.principal.id), params)
    	flash.message = "Your rating has been saved."
        redirect(action: "showEmployeeRatings", id: params.employeeRated.id)
	}
}
