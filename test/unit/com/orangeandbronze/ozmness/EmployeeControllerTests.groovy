package com.orangeandbronze.ozmness

import javax.swing.text.Position;

import org.hibernate.validator.AssertTrue;

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
	    mockSpringSecurityService.demand.encodePassword() {String p, Object s -> "encrypted"}
	    mockSpringSecurityService.demand.reauthenticate() {String u, String p -> }
	    mockSpringSecurityService.demand.getLoggedIn() {-> true}
	    mockSpringSecurityService.demand.getPrincipal() {-> ["username": "Bob"]}
	    controller.springSecurityService = mockSpringSecurityService.createMock() 
		
		mentorPosition = new EmployeePosition(name :"Senior", recommendedRating:3.0 )
		employeePosition = new EmployeePosition(name :"Junior", recommendedRating:1.0 )
		mockDomain(EmployeePosition, [mentorPosition, employeePosition])
		
		mentor1 = new Employee(username:"pepe", password:"12345", name:"Pepe", enabled:true, position:mentorPosition)
		mentor2 = new Employee(username:"ria", password:"12345", name:"Ria", enabled:true, position: mentorPosition)
		employee1 = new Employee(username:"mickey", password:"12345", name:"Mickey", enabled:"true", mentor:mentor1 ,position:employeePosition)
		mockDomain(Employee, [mentor1, mentor2, employee1])
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
		
		controller.params.username = "april"
		controller.params.name = "April"
		controller.params.password = "12345"
		controller.params."position.id" = mentorPosition.id
		controller.params.enabled = "true"
		controller.params.accountExpired = "false"
		controller.params.passwordExpired = "false"
		controller.params.accountLocked = "false"
		controller.params."mentor.id" = ""
		
		def parameters = controller.save()
		
		def employee = parameters.employeeInstance
		
		assertTrue employee.validate()
		
		println(employee.errors.errorCount + "WEEE")
		
		println("WEEE")
		
		 employee.errors.allErrors.each{
		    println(it)
		    println("-" * 20)
		  }
			
		
		assertEquals(employee, Employee.get(3))
		
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
