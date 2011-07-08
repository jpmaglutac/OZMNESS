package com.orangeandbronze.ozmness

class Technology {

	String name
	Technology parent

    static constraints = {
    	name nullable: false, blank: false
    	parent nullable: true
    }
	
	String toString(){
		return name
	}
}
