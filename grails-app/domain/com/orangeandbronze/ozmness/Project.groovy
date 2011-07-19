package com.orangeandbronze.ozmness

class Project {
	
	String name
	Employee lead
	static hasMany = [technologies:Technology, collaborators: Employee]

    static constraints = {
		name nullable:false, blank:false
		lead nullable:true
    }
	
	String toString(){
		return name
	}
}
