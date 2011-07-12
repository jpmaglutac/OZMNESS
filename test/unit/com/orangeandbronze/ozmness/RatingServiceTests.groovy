package com.orangeandbronze.ozmness

import grails.test.*

class RatingServiceTests extends GrailsUnitTestCase {
	
	def ratingService
	
	Employee higher
	Employee lower
	Employee mid
	EmployeePosition position
	
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
        mockDomain(Employee, [higher, mid, lower])
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
}
