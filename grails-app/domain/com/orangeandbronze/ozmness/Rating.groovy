package com.orangeandbronze.ozmness

class Rating {

	double value
	String comment
	Date dateCreated
	Employee creator
	Employee employeeRated
	Technology technology
	
    static constraints = {
    	value min:1.0d, max:3.0d
    	comment size:0..65534, nullable: true, blank: true
    	technology nullable: false
    }
    
    static mapping = {
    	comment type: 'text'
    }
	
	String toString(){
		return "${technology}: ${value}"
	}
}
