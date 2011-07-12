package com.orangeandbronze.ozmness

import grails.test.*

class RatingServiceTests extends GrailsUnitTestCase {
	
	def ratingService
	
	Employee higher
	Employee lower
	Employee mid
	
    protected void setUp() {
        super.setUp()
		ratingService = new RatingService()
		EmployeePosition position = new EmployeePosition(name: "dev", recommendedRating: 2.0)
		higher = new Employee(username: "higher", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position)
		mid = new Employee(username: "mid", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position, mentor: higher)
		lower = new Employee(username: "lower", password: "pass", enabled: true, accountExpired: false, 
            accountLocked: false, passwordExpired: false, position: position, mentor: mid)
        Project project = new Project(name: "project", lead: higher)
        mockDomain(Project, [project])
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
}
