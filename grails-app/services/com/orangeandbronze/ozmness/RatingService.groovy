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
	
	void saveRatingForm(Employee evaluator, def params){
		def technologies = Technology.list()
		technologies.each { technology ->
			def ratingInfo = evaluateRatingParams(technology.id, params)
			if(ratingInfo){
				def rating = getRatingUsingCriteria(evaluator, technology, Employee.get(params."employeeRated.id"))
				if(rating.size() > 0){
					rating = rating[0]
					rating.properties = ratingInfo
				}else{
					rating = new Rating(ratingInfo)
					rating.creator = evaluator
				}
				rating.save(flush: true)
			}
		}
	}
	
	def evaluateRatingParams(def techId, def params){
		def rating = params."${techId}_rating"
		if(rating){
			def comment = (params."${techId}_comment")?:" "
			return ["employeeRated.id": params."employeeRated.id", "technology.id": techId, "rating": rating, "comment": comment]
		} else {
			return null
		}
	}
	
	def getRatingUsingCriteria(def evaluator, def technology, def evaluated){
		return Rating.withCriteria {
			eq("creator", evaluator)
			eq("technology", technology)
			eq("employeeRated", evaluated)
		}
	}
	
	List listAllRateableEmployees(long raterID){
		def employeeInstance = Employee.get(raterID)
		
		def rateableEmployees = Employee.findAllByMentor(employeeInstance)
		rateableEmployees.add(employeeInstance)
		
		def projects = Project.findAllByLead(employeeInstance)
		
		projects.each {
			def collaborators = it.collaborators
			rateableEmployees.addAll(collaborators)
		}
		
		rateableEmployees.unique()
		
		return rateableEmployees
	}
}
