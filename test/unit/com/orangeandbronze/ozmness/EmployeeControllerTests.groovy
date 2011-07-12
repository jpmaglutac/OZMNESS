package com.orangeandbronze.ozmness

import grails.plugins.springsecurity.SpringSecurityService;
import grails.test.*

class EmployeeControllerTests extends ControllerUnitTestCase {
	

	Employee mentor1
	Employee mentor2
	Employee employee1
	EmployeePosition mentorPosition
	EmployeePosition employeePosition
	
    protected void setUp() {
        super.setUp()
		def mockSpringSecurityService = mockFor(SpringSecurityService, true)
		mockSpringSecurityService.demand.encodePassword() {String p-> "encrypted"}
		controller.springSecurityService = mockSpringSecurityService.createMock() 
		
		mentorPosition = new EmployeePosition(name :"Senior", recommendedRating:3.0 )
		employeePosition = new EmployeePosition(name :"Junior", recommendedRating:1.0 )
		mockDomain(EmployeePosition, [mentorPosition, employeePosition])
		
		mentor1 = new Employee(username:"pepe", password:"12345", name:"Pepe", enabled:true, position:mentorPosition)
		mentor2 = new Employee(username:"ria", password:"12345", name:"Ria", enabled:true, position: mentorPosition)
		employee1 = new Employee(username:"mickey", password:"12345", name:"Mickey", enabled:"true", mentor:mentor1 ,position:employeePosition)
		mockDomain(Employee, [mentor1, mentor2, employee1])
		
		def devRole = new Role(authority:"ROLE_DEV")
		mockDomain(Role,[devRole])
		
		mockDomain(UserRole)
    }

    protected void tearDown() {
        super.tearDown()

    }

    void testList() {
		def employeeList = Employee.list()
		
		assertEquals(3, Employee.count())
		
		def i = 1
		employeeList.each {
			assertEquals(i, it.id)
			i++  
		}
    }
	
	void testSave() {
		mockForConstraintsTests(Employee)
		
		def parameters = controller.save()
		
		assertEquals 4, controller.redirectArgs["id"]
		assertEquals "show", controller.redirectArgs["action"]
		
	}
	
	void testShow() {
		
		mockForConstraintsTests(Employee)
		
		for( i in 1..3){
		
			controller.params.id = i
			
			def parameters = controller.show()
			
			def employeeInstance = parameters.employeeInstance
			
			assertTrue employeeInstance.validate()
		
		}
	}
	
}
