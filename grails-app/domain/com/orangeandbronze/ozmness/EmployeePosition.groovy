package com.orangeandbronze.ozmness

class EmployeePosition {

	String name
	double recommendedRating

    static constraints = {
    	name nullable: false, blank: false
    	recommendedRating min:1.0d, max:3.0d
    }
	
	String toString(){
		return name
	}
}
