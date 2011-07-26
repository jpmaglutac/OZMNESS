package com.orangeandbronze.ozmness

import javassist.bytecode.StackMapTable.NewRemover;

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
		def avgRatings = []
		def techList = []
		def employee = Employee.get(employeeId)
		def ratingList = Rating.findAllByEmployeeRated(employee)
		Technology.list().each{ tech ->
			def orderRatings = orderRatingsByEvaluator(employee, tech, ratingList)
			separateRatings << orderRatings.ratingsByEvaluator
			avgRatings << orderRatings.avgRating
			techList << tech
		}
		return [allRatings:separateRatings,  avgRatings:avgRatings, techs: techList]
	}
	
	def orderRatingsByEvaluator(def evaluated, def tech, def ratingList){
		def ratingsByEvaluator = []
		double avgRating = 0
		def possibleEvaluators = getPossibleEvaluators(evaluated)
		int numberOfActualEvaluators = 0
		possibleEvaluators.each{ evaluator ->
			def ratingresult = ratingList.find { 
				it.technology.id == tech.id && it.creator.id == evaluator.id
			}
			if(ratingresult){
				avgRating += ratingresult?.rating?.value
				if(ratingresult?.rating?.value >0){
					numberOfActualEvaluators++
				}
				ratingsByEvaluator << ratingresult
			}else{
				ratingsByEvaluator << [creator: evaluator, technology: tech, employeeRated: evaluated]
			}
		}
		
		if(numberOfActualEvaluators>0){
			avgRating /= numberOfActualEvaluators
		}
		
		println avgRating
		
		return [ratingsByEvaluator: ratingsByEvaluator, avgRating:avgRating]
	}
	
	def getPossibleEvaluators(def employee){
		return ([employee] + [employee.mentor?:[]] + getLeads(employee)).flatten().unique()
	}
	
	
	def getLeads(def employee){
		def leads = []
		Project.list().each {
			if(it.collaborators.contains(employee)){
				if(it.lead)
					leads<<it.lead
			}
		}
		return leads
	}
	
	def getPreviousRatings(String rateeId, long raterId){
		def ratee = Employee.get(rateeId)
		def rater = Employee.get(raterId)
		def technologies = Technology.list()
		def ratingList = []
		
		technologies.each { technology->
			
			def rating = getRatingUsingCriteria(rater, technology, ratee)
			if(rating){
				ratingList += rating
			}else{
				ratingList << [technology: technology, rating: RatingValue.NA, comment: "" ]
			}			
		}
		println ratingList
		return ratingList
		
		
	}
	
	
}
