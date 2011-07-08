package com.orangeandbronze.ozmness

class Employee extends User{

	static hasMany = [projects:Project]
	Employee mentor
	EmployeePosition position
	
	
    static constraints = {
		position nullable:false
		mentor nullable:true
    }
	
	String toString() {
		return username
	}
}
