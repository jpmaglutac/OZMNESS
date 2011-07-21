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
	
	def separateRatingsPerCreator(def employeeId){
		def separateRatings = []
		def employee = Employee.get(employeeId)
		def ratingList = Rating.findAllByEmployeeRated(employee)
		getPossibleEvaluators(employee).each{ evaluator ->
			separateRatings << orderRatingsByTechnology(evaluator, ratingList)
		}
		println separateRatings
		return separateRatings
	}
	
	def orderRatingsByTechnology(def evaluator, def ratingList){
		def ratingsByTechnology = []
		Technology.list().each{ tech ->
			ratingsByTechnology << ratingList.find { it.technology == tech && it.creator == evaluator }
		}
		return ratingsByTechnology
	}
	
	def getPossibleEvaluators(def employee){
		return ([employee] + [employee.mentor?:[]] + getLeads(employee)).flatten().unique()
	}
	
	def getLeads(def employee){
		def leads = []
		employee.projects.each {
			leads << it.lead
		}
		return leads
	}
	
	
}
