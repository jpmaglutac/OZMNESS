package com.orangeandbronze.ozmness

class Employee extends User{

	static hasMany = [projects:Project]
	static belongsTo = [Project]
	String name
	
	Employee mentor
	EmployeePosition position
	
	
    static constraints = {
		position nullable:false
		name nullable:false
		mentor nullable:true
    }
	
	String toString() {
		return username
	}
}
