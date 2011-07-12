package com.orangeandbronze.ozmness

class TechnologyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [technologyInstanceList: Technology.list(params), technologyInstanceTotal: Technology.count()]
    }

    def create = {
        def technologyInstance = new Technology()
        technologyInstance.properties = params
        return [technologyInstance: technologyInstance]
    }

    def save = {
        def technologyInstance = new Technology(params)
        if (technologyInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'technology.label', default: 'Technology'), '\"' + technologyInstance.name + '\"'])}"
            redirect(action: "show", id: technologyInstance.id)
        }
        else {
            render(view: "create", model: [technologyInstance: technologyInstance])
        }
    }

    def show = {
        def technologyInstance = Technology.get(params.id)
        if (!technologyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technology.label', default: 'Technology'), params.id])}"
            redirect(action: "list")
        }
        else {
            [technologyInstance: technologyInstance]
        }
    }

    def edit = {
        def technologyInstance = Technology.get(params.id)
        if (!technologyInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technology.label', default: 'Technology'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [technologyInstance: technologyInstance]
        }
    }

    def update = {
        def technologyInstance = Technology.get(params.id)
        if (technologyInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (technologyInstance.version > version) {
                    
                    technologyInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'technology.label', default: 'Technology')] as Object[], "Another user has updated Technology \"" + technologyInstance.name + "\" while you were editing")
                    render(view: "edit", model: [technologyInstance: technologyInstance])
                    return
                }
            }
			if(params.id == params.parent.id) {
				flash.message = "You cannot declare \"" + technologyInstance.name + "\" as its own parent!"
				render(view: "edit", model: [technologyInstance: technologyInstance])
				return
			}
            technologyInstance.properties = params
            if (!technologyInstance.hasErrors() && technologyInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'technology.label', default: 'Technology'), '\"' + technologyInstance.name + '\"'])}"
                redirect(action: "show", id: technologyInstance.id)
            }
            else {
                render(view: "edit", model: [technologyInstance: technologyInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'technology.label', default: 'Technology'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def technologyInstance = Technology.get(params.id)
        if (technologyInstance) {
            try {
                technologyInstance.delete(flush: true)
                flash.message = "Technology has been deleted"
                redirect(action: "list")
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
