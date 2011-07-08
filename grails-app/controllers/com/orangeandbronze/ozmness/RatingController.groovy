package com.orangeandbronze.ozmness

class RatingController {
	
	def springSecurityService
	def ratingService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ratingInstanceList: Rating.list(params), ratingInstanceTotal: Rating.count()]
    }

    def create = {
        def ratingInstance = new Rating()
        ratingInstance.properties = params
        def canBeRated = ratingService.getEmployeesThatCanBeRated(Employee.get(springSecurityService.principal.id))
        return [canBeRated: canBeRated, ratingInstance: ratingInstance]
    }

    def save = {
        def ratingInstance = new Rating(params)
		ratingInstance.creator = Employee.get(springSecurityService.principal.id)
        if (ratingInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'rating.label', default: 'Rating'), ratingInstance.id])}"
            redirect(action: "show", id: ratingInstance.id)
        }
        else {
            render(view: "create", model: [canBeRated: ratingService.getEmployeesThatCanBeRated(Employee.get(springSecurityService.principal.id)), ratingInstance: ratingInstance])
        }
    }

    def show = {
        def ratingInstance = Rating.get(params.id)
        if (!ratingInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
            redirect(action: "list")
        }
        else {
            [ratingInstance: ratingInstance]
        }
    }

    def edit = {
        def ratingInstance = Rating.get(params.id)
		if(Employee.get(springSecurityService.principal.id) == ratingInstance.creator || Role.get(springSecurityService.principal.id)) {
		    if (!ratingInstance) {
		        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
		        redirect(action: "list")
		    }
		    else {
		        return [ratingInstance: ratingInstance]
		    }
		}
		else {
			flash.message = "You are not allowed to edit this entry."
			redirect(action: "list")
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
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'rating.label', default: 'Rating'), ratingInstance.id])}"
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
		if(Employee.get(springSecurityService.principal.id) == ratingInstance.creator || Role.get(springSecurityService.principal.id)) {
	        if (ratingInstance) {
	            try {
	                ratingInstance.delete(flush: true)
	                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'rating.label', default: 'Rating'), params.id])}"
	                redirect(action: "list")
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
		else {
			flash.message = "You are not allowed to delete this entry."
			redirect(action: "list")
		}
    }
}
