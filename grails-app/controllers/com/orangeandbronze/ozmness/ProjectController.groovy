package com.orangeandbronze.ozmness

class ProjectController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
    }

    def create = {
        def projectInstance = new Project()
        projectInstance.properties = params
        return [projectInstance: projectInstance]
    }

    def save = {
        def projectInstance = new Project(params)
        if (projectInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])}"
            redirect(action: "show", id: projectInstance.id)
        }
        else {
            render(view: "create", model: [projectInstance: projectInstance])
        }
    }

    def show = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
        else {
            [projectInstance: projectInstance]
        }
    }

    def edit = {
        def projectInstance = Project.get(params.id)
        if (!projectInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [projectInstance: projectInstance]
        }
    }

    def update = {
        def projectInstance = Project.get(params.id)
        if (projectInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (projectInstance.version > version) {
                    
                    projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'project.label', default: 'Project')] as Object[], "Another user has updated this Project while you were editing")
                    render(view: "edit", model: [projectInstance: projectInstance])
                    return
                }
            }
            projectInstance.properties = params
            if (!projectInstance.hasErrors() && projectInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), projectInstance.id])}"
                redirect(action: "show", id: projectInstance.id)
            }
            else {
                render(view: "edit", model: [projectInstance: projectInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def projectInstance = Project.get(params.id)
        if (projectInstance) {
            try {
                projectInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def showPossibleCollaborators =  {
		def projectInstance = Project.get(params.id)
		if(projectInstance){
			def possibleCollaborators = Employee.list() - projectInstance.collaborators 
			
			return  [projectInstance: projectInstance, possibleCollaborators: possibleCollaborators]
		}
	}
	
	def addCollaborator ={
		def employeeInstance = Employee.get(params.collaboratorID)
		def projectInstance = Project.get(params.id)
		if(employeeInstance && projectInstance){
			try{
				projectInstance.addToCollaborators(employeeInstance)
				projectInstance.save(flush: true)
				employeeInstance.addToProjects(projectInstance)
				employeeInstance.save(flush: true)
				redirect(action: "show", id: params.id)
			}catch (e) {
                flash.message = "${message(code: 'default.not.added.message', args: [message(code: 'project.collaborators', default: 'Project'), params.collaboratorID])}"
                redirect(action: "show", collaboratorID: params.collaboratorID)
            }
		}
		
		
    }
}
