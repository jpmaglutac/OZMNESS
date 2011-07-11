package com.orangeandbronze.ozmness

import grails.test.*

class RatingServiceTests extends GrailsUnitTestCase {
	
	def ratingService
	
	Employee higher
	Employee lower
	
    protected void setUp() {
        super.setUp()
		ratingService = new RatingService()
		EmployeePosition position = new EmployeePosition(name: "dev", recommendedRating: 2.0)
		higher = new Employee(username: "higher", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position)
		lower = new Employee(username: "lower", password: "pass", enabled: true, accountExpired: false, 
            accountLocked: false, passwordExpired: false, position: position, mentor: higher)
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testMentorOnIsMentorReturnsTrue() {
		assertTrue(ratingService.isMentor(higher, lower))
	}
	
	void testNotMentorOnIsMentorReturnsFalse() {
		assertFalse(ratingService.isMentor(lower, lower))
	}
}
