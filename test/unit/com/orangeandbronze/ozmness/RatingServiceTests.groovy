package com.orangeandbronze.ozmness

import grails.test.*

class RatingServiceTests extends GrailsUnitTestCase {
	
	def ratingService
	
	Employee higher
	Employee lower
	Employee mid
	EmployeePosition position
	Technology tech
	
    protected void setUp() {
        super.setUp()
        
		ratingService = new RatingService()
		position = new EmployeePosition(name: "dev", recommendedRating: 2.0)
		higher = new Employee(username: "higher", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position)
		mid = new Employee(username: "mid", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position, mentor: higher)
		lower = new Employee(username: "lower", password: "pass", enabled: true, accountExpired: false, 
            accountLocked: false, passwordExpired: false, position: position, mentor: mid)
        Project project = new Project(name: "project", lead: higher)
        tech = new Technology(name: "tech")
        mockDomain(Technology, [tech])
        mockDomain(Employee, [higher, mid, lower])
        mockDomain(Project, [project])
        mockDomain(Rating)
        project.addToCollaborators(mid)
        project.addToCollaborators(lower)
        
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testMentorOnIsMentorReturnsTrue() {
		assertTrue(ratingService.isMentor(higher, mid))
	}
	
	void testNotMentorOnIsMentorReturnsFalse() {
		assertFalse(ratingService.isMentor(lower, lower))
	}
	
	void testTechLeadOnIsTechLeadReturnsTrue(){
		assertTrue(ratingService.isTechLead(higher, lower))
	}
	
	void testNotTechLeadOnIsTechLeadReturnsFalse(){
		assertFalse(ratingService.isTechLead(mid, lower))
	}
	
	void testSameEmployeeOnCanRateEmployeeReturnsTrue(){
		assertTrue(ratingService.canRateEmployee(mid, mid))
	}
	
	void testMentorOnCanRateEmployeeReturnsTrue(){
		assertTrue(ratingService.canRateEmployee(mid, lower))
	}
	
	void testTechLeadOnCanRateEmployeeReturnsTrue(){
		assertTrue(ratingService.canRateEmployee(higher, lower))
	}
	
	void testCannotRateOnCanRateEmployeeReturnsFalse(){
		assertFalse(ratingService.canRateEmployee(mid, higher))
	}
	
	void testGetMenteesReturnsMentees(){
		Employee newEmp = new Employee(username: "new", password: "pass", enabled: true, accountExpired: false, 
            accountLocked: false, passwordExpired: false, position: position, mentor: higher)
        mockDomain(Employee, [higher, lower, mid, newEmp])           
        def mentees = ratingService.getMentees(higher)
        assertEquals(2,mentees.size())
        assertTrue(mentees.contains(newEmp))
        assertTrue(mentees.contains(mid))
	}
	
	void testGetCollaboratorsReturnsCollaborators(){
		def collabs = ratingService.getCollaboratorsUnder(higher)
        assertEquals(2,collabs.size())
        assertTrue(collabs.contains(lower))
        assertTrue(collabs.contains(mid))
	}
	
	void testGetEmployeesThatCanBeRatedReturnsEmployeesThatCanBeRated(){
		def ratees = ratingService.getEmployeesThatCanBeRated(higher)
		assertEquals(3, ratees.size)
		assertTrue(ratees.contains(higher))
        assertTrue(ratees.contains(lower))
        assertTrue(ratees.contains(mid))
	}
	
	void testSaveRatingFormOnOneTechnology() {
		def params = [:]
		params.put("employeeRated.id", 2)
		params.put("1_rating", "TWO")
		params.put("1_comment", "Comment")
		RatingService.metaClass.getRatingUsingCriteria = { evaluator, technology, evaluated -> return [] }
		ratingService.saveRatingForm(higher, params)
		
		assertEquals(1, Rating.count())
		def rating = Rating.get(1)
		assertNotNull(rating)
		assertEquals(2, rating.rating.value)
		assertEquals(tech, rating.technology)
		assertEquals(higher, rating.creator)
		assertEquals(mid, rating.employeeRated)
		assertEquals("Comment", rating.comment)
	}
	
	void testSaveRatingFormWithExistingRating() {
		def params = [:]
		params.put("employeeRated.id", 2)
		params.put("1_rating", "TWO")
		params.put("1_comment", "Comment")
		def newRating = new Rating(rating: RatingValue.ONE, comment: "New", technology: tech, employeeRated: mid, creator: higher, dateCreated: new Date())
		mockDomain(Rating, [newRating])
		RatingService.metaClass.getRatingUsingCriteria = { evaluator, technology, evaluated ->
        	return (evaluator == newRating.creator && technology == newRating.technology && evaluated == newRating.employeeRated)?
        		[newRating] : []
        } 
		ratingService.saveRatingForm(higher, params)
		
		assertEquals(1, Rating.count())
		def rating = Rating.get(1)
		assertNotNull(rating)
		assertEquals(2, rating.rating.value)
		assertEquals(tech, rating.technology)
		assertEquals(higher, rating.creator)
		assertEquals(mid, rating.employeeRated)
		assertEquals("Comment", rating.comment)
	}
	
	void testSaveRatingFormOnTwoTechnologies() {
		def newTech = new Technology(name: "new").save(flush: true)
		mockDomain(Technology, [tech, newTech])
	
		def params = [:]
		params.put("employeeRated.id", 2)
		params.put("1_rating", "TWO")
		params.put("1_comment", "Comment")
		params.put("2_rating", "NA")
		RatingService.metaClass.getRatingUsingCriteria = { evaluator, technology, evaluated -> return [] }
		
		ratingService.saveRatingForm(higher, params)
		
		assertEquals(2, Rating.count())
		
		assertEquals(2, Rating.get(1).rating.value)
		assertEquals(" ", Rating.get(2).comment)
	}
	
	void testEvaluateRatingParamsReturnsCorrectly() {
		def params = [:]
		params.put("employeeRated.id", 2)
		params.put("1_rating", "TWO")
		params.put("1_comment", "Comment")
		
		def ratingInfo = ratingService.evaluateRatingParams(1, params)
		assertNotNull(ratingInfo)
		assertEquals(ratingInfo.rating, params."1_rating")
	}
	
	void testEvaluateRatingParamsReturnsNull() {
		def params = [:]
		params.put("employeeRated.id", 2)
		params.put("2_rating", "TWO")
		params.put("2_comment", "Comment")
		
		assertNull(ratingService.evaluateRatingParams(1, params))
	}
	
}
