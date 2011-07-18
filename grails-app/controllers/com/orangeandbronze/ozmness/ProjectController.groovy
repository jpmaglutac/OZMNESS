package com.orangeandbronze.ozmness

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class ProjectController {

	def springSecurityService

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
						projectInstance.save(flush: true)
						flash.message = "\"" + employee + "\" has been added to the project"
					}
				}
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
		redirect(action: show, id: params.projectId)
	}
}
