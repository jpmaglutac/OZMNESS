package com.orangeandbronze.ozmness

import grails.test.*

class ProjectControllerTests extends ControllerUnitTestCase {
	
	Employee higher
	Employee lower
	Employee mid
	EmployeePosition position
	Project project
	
	protected void setUp() {
		super.setUp()
		position = new EmployeePosition(name: "dev", recommendedRating: 2.0)
		higher = new Employee(username: "higher", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position)
		mid = new Employee(username: "mid", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position, mentor: higher)
		lower = new Employee(username: "lower", password: "pass", enabled: true, accountExpired: false,
			accountLocked: false, passwordExpired: false, position: position, mentor: mid)
		project = new Project(name: "project", lead: higher)
		mockDomain(Employee, [higher, mid, lower])
		mockDomain(Project, [project])
	}

    protected void tearDown() {
        super.tearDown()
    }

    void testShowPossibleCollaborators() {
		controller.params.id = 1
		def showPossibleCollaborators = controller.showPossibleCollaborators()
		
		assertEquals(project, showPossibleCollaborators.projectInstance)
		def collabs = showPossibleCollaborators.possibleCollaborators
		assertNotNull(collabs)
		assertTrue(collabs.contains(mid))
		assertTrue(collabs.contains(lower))
		assertFalse(collabs.contains(higher))
    }
	
	void testShowPossibleCollaboratorsAfterAddingACollaborator() {
		controller.params.id = 1
		project.addToCollaborators(mid)
		def showPossibleCollaborators = controller.showPossibleCollaborators()
		
		assertEquals(project, showPossibleCollaborators.projectInstance)
		def collabs = showPossibleCollaborators.possibleCollaborators
		assertNotNull(collabs)
		assertFalse(collabs.contains(mid))
		assertTrue(collabs.contains(lower))
		assertFalse(collabs.contains(higher))
	}
	
	void testAddOneCollaborator() {
		controller.params.id = 1
		controller.params.collaboratorID = 2
		controller.addCollaborator()
		
		assertNotNull(project.collaborators)
		assertTrue(project.collaborators.contains(mid))
		assertFalse(project.collaborators.contains(lower))
		
	}
	
	void testAddMoreThanOneCollaborator() {
		controller.params.id = 1
		controller.params.collaboratorID = [2,3]
		controller.addCollaborator()
		
		assertNotNull(project.collaborators)
		assertTrue(project.collaborators.contains(mid))
		assertTrue(project.collaborators.contains(lower))
	}
	
	void testRemoveCollaborator() {
		project.addToCollaborators(mid)
		project.addToCollaborators(lower)
		controller.params.projectId = 1
		controller.params.id = 2
		
		controller.removeCollaborator()
		
		assertNotNull(project.collaborators)
		assertFalse(project.collaborators.contains(mid))
		assertTrue(project.collaborators.contains(lower))
	}
	
	void testRemoveCollaboratorOnWrongId() {
		project.addToCollaborators(mid)
		project.addToCollaborators(lower)
		controller.params.projectId = 1
		controller.params.id = 1
		
		controller.removeCollaborator()
		
		assertNotNull(project.collaborators)
		assertTrue(project.collaborators.contains(mid))
		assertTrue(project.collaborators.contains(lower))
	}
	
	void testRemoveCollaboratorOnInexistentId() {
		project.addToCollaborators(mid)
		project.addToCollaborators(lower)
		controller.params.projectId = 1
		controller.params.id = 4
		
		controller.removeCollaborator()
		
		assertTrue(controller.flash.message.startsWith("Could not find"))
	}
}
