package com.orangeandbronze.ozmness

import grails.test.*

class RatingControllerTests extends ControllerUnitTestCase {
    
    Employee higher
	Employee lower
	Employee mid
	EmployeePosition position
	Technology tech
	
    protected void setUp() {
        super.setUp()
        controller.ratingService = new RatingService()
        controller.metaClass.message = {}
		position = new EmployeePosition(name: "dev", recommendedRating: 2.0)
		higher = new Employee(username: "higher", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position)
		mid = new Employee(username: "mid", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position, mentor: higher)
		lower = new Employee(username: "lower", password: "pass", enabled: true, accountExpired: false, 
            accountLocked: false, passwordExpired: false, position: position, mentor: mid)
        tech = new Technology(name: "tech")
        Project project = new Project(name: "project", lead: higher)
        mockDomain(Employee, [higher, mid, lower])
        mockDomain(Project, [project])
        mockDomain(Technology, [tech])
        project.addToTechnologies(tech)
        project.addToCollaborators(mid)
        project.addToCollaborators(lower)
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	void testCreateWillReturnCanBeRated(){
		controller.springSecurityService = [principal:[id: 2l]]
		def create = controller.create()
		
		assertNotNull(create.canBeRated)
		assertEquals(2, create.canBeRated.size())
		assertTrue(create.canBeRated.contains(lower))
	}

    void testSaveWillSaveIntoDomain() {
    	def ratings = []
		controller.springSecurityService = [principal:[id: 1l]]
    	mockDomain(Rating, ratings)
		controller.params.rating = "TWO"
		controller.params.comment = "New Comment"
		controller.params."technology.id" = tech.id
		controller.params."employeeRated.id" = lower.id
		
		controller.save()
		assertEquals(1, Rating.count())
		assertNotNull(Rating.findByCreator(higher))
    }
	
	void testErrorsWillNotSaveIntoDomain() {
		def ratings = []
		controller.springSecurityService = [principal:[id: 1l]]
		mockDomain(Rating, ratings)
		controller.params.comment = "New Comment"
		controller.params."technology.id" = tech.id
		controller.params."employeeRated.id" = lower.id
		
		controller.save()
		assertEquals(0, Rating.count())
		assertNull(Rating.get(1))
	}
    
}
