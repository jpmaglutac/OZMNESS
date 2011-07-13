package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class RatingController {
	
	def springSecurityService
	def ratingService

    static allowedMethods = [save: "POST", update: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
		if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN") || params.id == null) {
			params.max = Math.min(params.max ? params.int('max') : 10, 100)
			[ratingInstanceList: Rating.list(params), ratingInstanceTotal: Rating.count()]
		} else {
			redirect(controller: "employee", action: "list")
		}
    }

    def create = {
		if(params.id == null) redirect(controller: "employee", action: "list")
        def ratingInstance = new Rating(params)
        def canBeRated = ratingService.getEmployeesThatCanBeRated(Employee.get(springSecurityService.principal.id))
        return [canBeRated: canBeRated, ratingInstance: ratingInstance]
    }

    def save = {
        def ratingInstance = new Rating(params)
        def loggedInEmployee = Employee.get(springSecurityService.principal.id)
		ratingInstance.creator = loggedInEmployee
        if (ratingInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'employee.label', default: 'Employee'), params.id])}"
            redirect(action: "show", id: ratingInstance.id)
        }
        else {
            render(view: "create", model: [canBeRated: ratingService.getEmployeesThatCanBeRated(loggedInEmployee), ratingInstance: ratingInstance])
        }
    }

    def show = {
		def loggedInUser = Employee.get(springSecurityService.principal.id)
        def ratingInstance = Rating.get(params.id)
        if (!ratingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
            redirect(action: "list")
        }
        else {
            [loggedInUser: loggedInUser, ratingInstance: ratingInstance]
        }
    }

    def edit = {
        def ratingInstance = Rating.get(params.id)
	    if (!ratingInstance) {
	        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
	        if(SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
				redirect(action: "list")
	        } else {
				redirect(controller: "employee", action: "list")
			}
	    }
	    else {
			if(Employee.get(springSecurityService.principal.id) == ratingInstance.creator || SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
				if(params.employee != null) return [ratingInstance: ratingInstance]
				else return [employeeID: params.employee, ratingInstance: ratingInstance]
			} else {
				flash.message = "You are not allowed to edit this rating!"
				redirect(controller: "employee", action: "list")
			}
		}
    }

    def update = {
        def ratingInstance = Rating.get(params.id)
        if (ratingInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (ratingInstance.version > version) {
                    
                    ratingInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'rating.label', default: 'Rating')] as Object[], "Another user has updated this Rating while you were editing")
                    render(view: "edit", model: [ratingInstance: ratingInstance])
                    return
                }
            }
            ratingInstance.properties = params
            if (!ratingInstance.hasErrors() && ratingInstance.save(flush: true)) {
                flash.message = "Rating for " + ratingInstance.employeeRated + " has been updated."
				redirect(action: "show", id: ratingInstance.id)
            }
            else {
                render(view: "edit", model: [ratingInstance: ratingInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def ratingInstance = Rating.get(params.id)
        if (ratingInstance) {
            try {
				if(Employee.get(springSecurityService.principal.id) == ratingInstance.creator || SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
					ratingInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
					if(params.employee != null) redirect(controller: "employee", action: "showEmployeeRatings", id: params.employee)
					else redirect(action: "list")
				} else {
					flash.message = "You are not allowed to delete this rating!"
					redirect(controller: "rating", action: "show", id: params.id)
				}
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
            redirect(action: "list")
        }
    }
}
