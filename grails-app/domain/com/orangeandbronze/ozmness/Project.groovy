package com.orangeandbronze.ozmness

class Project {
	
	String name
	Employee lead
	static hasMany = [technologies:Technology]

    static constraints = {
		name nullable:false, blank:false
		lead nullable:false
		
    }
	
	String toString(){
		return name
	}
}
