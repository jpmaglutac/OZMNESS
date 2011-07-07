package com.orangeandbronze.ozmness

class Position {

	String name
	double recommendedRating

    static constraints = {
    	name nullable: false, blank: false
    	value minSize:1.0, maxSize:3.0
    }
}
