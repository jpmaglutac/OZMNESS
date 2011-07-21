package com.orangeandbronze.ozmness

class Rating {

	RatingValue rating
	String comment
	Date dateCreated
	Employee creator
	Employee employeeRated
	Technology technology
	
    static constraints = {
    	rating nullable: false
    	comment size:0..65534, nullable: true, blank: true
    	technology nullable: false
    }
    
    static mapping = {
    	comment type: 'text'
    }
	
	String toString(){
		return "${technology}: ${rating.name}"
	}
}
