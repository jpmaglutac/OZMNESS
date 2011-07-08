package com.orangeandbronze.ozmness

class RatingService {

    static transactional = true
    
    def getEmployeesThatCanBeRated(Employee evaluator){
    	def employeesThatCanBeRated = []
    	employeesThatCanBeRated += evaluator
    	employeesThatCanBeRated += getMentees(evaluator)
    	employeesThatCanBeRated += getCollaboratorsUnder(evaluator)
    	return (employeesThatCanBeRated.flatten() as Set)
    }
    
    def getCollaboratorsUnder(Employee evaluator){
    	def projects = Project.findAllByLead(evaluator)
    	def canBeRated = []
    	projects.each { project ->
    		canBeRated += projects.collaborators
    	}
    	return canBeRated
    }
    
    def getMentees(Employee evaluator){
    	return Employee.findAllByMentor(evaluator)
    }

    def canRateEmployee(Employee evaluator, Employee evaluated) {
		return (isSameEmployee(evaluator, evaluated)) || (isMentor(evaluator, evaluated)) || (isTechLead(evaluator, evaluated))
    }
	
	def isSameEmployee(Employee evaluator, Employee evaluated) {
		return evaluator == evaluated
	}
	
	def isMentor(Employee evaluator, Employee evaluated) {
		return evaluator == evaluated.mentor
	}
	
	def isTechLead(Employee evaluator, Employee evaluated) {
		evaluated.projects.each{ project ->
			if(project.lead == evaluator)
				return true
		}
		return false
	}
}
