package com.orangeandbronze.ozmness

class Employee extends User{

	static hasMany = [projects:Project]
	Employee mentor
	EmployeePosition position
	
	
    static constraints = {
		position nullable:false
    }
	
	String toString() {
		return username
	}
}
