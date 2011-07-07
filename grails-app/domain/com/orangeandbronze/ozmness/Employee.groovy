package com.orangeandbronze.ozmness

class Employee extends User{

	static hasMany = [projects:Project]
	Employee mentor
	Position position
	
	
    static constraints = {
		position nullable:false
    }
}
