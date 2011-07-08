package com.orangeandbronze.ozmness

class RatingService {

    static transactional = true

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
		for(Project project: evaluated.projects){
			if(project.lead == evaluator)
				return true
		}
		return false
	}
}
