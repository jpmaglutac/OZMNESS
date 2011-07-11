package com.orangeandbronze.ozmness

class RatingService {

    static transactional = true
    
    def getEmployeesThatCanBeRated(Employee evaluator){
		( [evaluator] + getMentees(evaluator) + getCollaboratorsUnder(evaluator) ).unique()
    }
    
    List getCollaboratorsUnder(Employee evaluator){
    	Project.findAllByLead(evaluator).collaborators.flatten()
    }
    
    def getMentees(Employee evaluator){
    	Employee.findAllByMentor(evaluator)
    }

    def canRateEmployee(Employee evaluator, Employee evaluated) {
		return ((evaluator == evaluated) || isMentor(evaluator, evaluated) || isTechLead(evaluator, evaluated))
    }
    
	
	def isMentor(Employee evaluator, Employee evaluated) {
		evaluator == evaluated.mentor
	}
	
	def isTechLead(Employee evaluator, Employee evaluated) {
		def retval = false
		Project.findAllByLead(evaluator).each{ project ->
			if(project.collaborators.id.contains(evaluated.id)){
				retval = true
			}
		}
		return retval
	}
}
