package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
class EmployeeController {
	
	def springSecurityService
	def ratingService

    static allowedMethods = [save: "POST", update: "POST"]

	def scaffold = true
		
	def save = {
		params.password = springSecurityService.encodePassword(params.password)
		def employeeInstance = new Employee(params)
		if (employeeInstance.save(flush: true)) {
			UserRole.create(employeeInstance, Role.findByAuthority("ROLE_DEV"))
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'employee.label', default: 'Employee'), '\"' + employeeInstance.username + '\"'])}"
			redirect(action: "show", id: employeeInstance.id)
		}
		else {
			render(view: "create", model: [employeeInstance: employeeInstance])
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
	
	def showEmployeeRatings ={
		def employeeInstance = Employee.get(params.id)
		def loggedInUser = Employee.get(springSecurityService.principal.id)
		if(employeeInstance){
			def ratings = Rating.findAllByEmployeeRated(employeeInstance)
			return [loggedInUser: loggedInUser, ratings: ratings]
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
		def ratingInstance = new Rating(params)
		ratingInstance.creator = Employee.get(springSecurityService.principal.id)
        if (ratingInstance.save(flush: true)) {
            flash.message = "Your rating for " + ratingInstance.employeeRated + " has been saved."
            redirect(action: "showEmployeeRatings", id: ratingInstance.employeeRated.id)
        }
        else {
            render(view: "rateEmployee", model: [employeeId: params.employeeRated.id, canBeRated: ratingService.getEmployeesThatCanBeRated(Employee.get(springSecurityService.principal.id)), ratingInstance: ratingInstance])
        }
	}
}
