package com.orangeandbronze.ozmness

import grails.test.*

class RatingValueTests extends GrailsUnitTestCase {
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
 
 	void testRatingValue() {
 		println(RatingValue.values())
 		println(RatingValue.values()*.name)
 		println(RatingValue.values()*.value)
 	}
    
}
