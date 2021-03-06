package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class ProjectController {

	def springSecurityService

	static allowedMethods = [save: "POST", update: "POST"]
	
	def scaffold = true

	def list = {
			params.max = Math.min(params.max ? params.int('max') : 10, 100)
			def loggedInUser = Employee.get(springSecurityService.principal.id)
			[loggedInUser: loggedInUser, projectInstanceList: Project.list(params), projectInstanceTotal: Project.count()]
		}
	
	def show = {
		def loggedInUser = Employee.get(springSecurityService.principal.id)
		def projectInstance = Project.get(params.id)
		if (!projectInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else {
			[loggedInUser: loggedInUser, projectInstance: projectInstance]
		}
	}
	
	def save = {
		def projectInstance = new Project(params)
		if (projectInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), '\"' + projectInstance.name + '\"'])}"
			redirect(action: "show", id: projectInstance.id)
		}
		else {
			render(view: "create", model: [projectInstance: projectInstance])
		}
	}

	def edit = {
		def projectInstance = Project.get(params.id)
		if (!projectInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])}"
			redirect(action: "list")
		}
		else {
			if(Employee.get(springSecurityService.principal.id) == projectInstance.lead || SpringSecurityUtils.ifAllGranted("ROLE_ADMIN")) {
				return [projectInstance: projectInstance]
			} else {
				flash.message = "You are not allowed to edit this project!"
				redirect(controller: "project", action: "show", id: params.id)
			}
		}
	}

	def update = {
		def projectInstance = Project.get(params.id)
		if (projectInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (projectInstance.version > version) {

					projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'project.label', default: 'Project')] as Object[], "Another user has updated Project \"" + projectInstance.name + "\" while you were editing")
					render(view: "edit", model: [projectInstance: projectInstance])
					return
				}
			}
			projectInstance.properties = params
			if (!projectInstance.hasErrors() && projectInstance.save(flush: true)) {
				if(projectInstance.collaborators.contains(projectInstance.lead))
					projectInstance.removeFromCollaborators(projectInstance.lead)
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), '"' + projectInstance.name + '"'])}"
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
	
	def showPossibleCollaborators =  {
		def projectInstance = Project.get(params.id)
		if(projectInstance){
			//had a bug with javassist domain wrapper
			//can't do Employee.list() - [projectInstance.lead]
			
			def possibleCollaboratorIds = Employee.list().id- projectInstance.collaborators?.id - [ projectInstance.lead.id ]
			def possibleCollaborators = possibleCollaboratorIds.collect{ Employee.get(it) }
			return  [projectInstance: projectInstance, possibleCollaborators: possibleCollaborators]
		} else {
			redirect(action: "list")
		}
	}
	
	def addCollaborator ={
		def projectInstance = Project.get(params.id)
		if(projectInstance){
			try{
				params.collaboratorID.each{
					def employee = Employee.get(it)
					if(employee){
						projectInstance.addToCollaborators(employee)
					}
				}
				
				if(params.collaboratorID.size() > 1)
					flash.message = "Collaborators added to project \"" + projectInstance.name + "\""
				else
					flash.message = "Collaborator added to project \"" + projectInstance.name + "\""
					
				redirect(action: "show", id: params.id)
			}catch (e) {
				flash.message = "${message(code: 'default.not.added.message', args: [message(code: 'project.collaborators', default: 'Project'), params.collaboratorID])}"
				redirect(action: "show", collaboratorID: params.collaboratorID)
			}
		}else{
			flash.message = "${message(code: 'default.not.added.message', args: [message(code: 'project.collaborators', default: 'Project'), params.collaboratorID])}"
			redirect(action: "show", id: params.id)
		}
	}
	
	def removeCollaborator = {
		def collaborator = Employee.get(params.id)
		def project = Project.get(params.projectId)
		if(collaborator){
			project.removeFromCollaborators(collaborator)
			flash.message = "Removed \"${collaborator}\" from the project"
		}else
			flash.message = "Could not find employee"
		redirect(action: "show", id: params.projectId)
	}
}
