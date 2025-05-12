package com.hexaware.interfaceimp;

import static org.junit.Assert.*;

import org.junit.Test;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.model.Employee;
//import com.hexaware.util.DbconnUtil;

import org.junit.After;
import org.junit.Before;
//import org.junit.Test;

//import java.sql.Connection;
//import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;


public class EmployeeServiceimpTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	 private EmployeeServiceimp employeeService;
	    private int testEmployeeId;

	    @Before
	    public void setUp() throws Exception {
	        employeeService = new EmployeeServiceimp();

	        // Create a test employee
	        Employee emp = new Employee(0, "Test", "User", LocalDate.of(1995, 5, 15), "Male",
	                "testuser@example.com", 9999999999L, "Test Address", "Tester",
	                LocalDate.of(2020, 1, 1), null);

	        employeeService.addEmployee(emp);

	        // Get the auto-generated ID (assuming email is unique)
	        List<Employee> allEmployees = employeeService.viewEmployee();
	        for (Employee e : allEmployees) {
	            if (e.getEmail().equals("testuser@example.com")) {
	                testEmployeeId = e.getEmployeeID(testEmployeeId);
	                break;
	            }
	        }
	    }

	    @After
	    public void tearDown() throws Exception {
	        // Delete the test employee from DB
	        //employeeService.deleteEmployee(testEmployeeId);
//	    	try {
//	            employeeService.employeeById(testEmployeeId); // Check if exists
//	            employeeService.deleteEmployee(testEmployeeId);
//	        } catch (EmployeeNotFoundException e) {
//	            // Already deleted in test case, ignore
//	        }
	    	
	    	  try {
	    	        employeeService.deleteEmployee(testEmployeeId);
	    	    } catch (EmployeeNotFoundException e) {
	    	        // Employee was already deleted; no action needed
	    	        System.out.println("tearDown: Employee already deleted.");
	    	    }
	    }

	    @Test
	    public void testAddAndGetEmployee() throws Exception {
	        Employee fetched = employeeService.employeeById(testEmployeeId);

	        assertNotNull(fetched);
	        assertEquals("Test", fetched.getFirstName());
	        assertEquals("User", fetched.getLastName());
	        assertEquals("testuser@example.com", fetched.getEmail());
	    }

	    @Test
	    public void testUpdateEmployee() throws Exception {
	        Employee emp = employeeService.employeeById(testEmployeeId);
	        emp.setAddress("Updated Address");
	        boolean updated = employeeService.updateEmployee(emp);
	        assertTrue(updated);

	        Employee updatedEmp = employeeService.employeeById(testEmployeeId);
	        assertEquals("Updated Address", updatedEmp.getAddress());
	    }

	    @Test
	    public void testViewEmployee() throws Exception {
	        List<Employee> employees = employeeService.viewEmployee();
	        assertNotNull(employees);
	        assertTrue(employees.size() > 0);
	    }

	    @Test(expected = EmployeeNotFoundException.class)
	    public void testDeleteEmployee() throws Exception {
//	        employeeService.deleteEmployee(testEmployeeId);
//
//	        // This should now throw EmployeeNotFoundException
//	        employeeService.employeeById(testEmployeeId);
	    	
//	    	   boolean deleted = employeeService.deleteEmployee(testEmployeeId);
//	    	    assertTrue(deleted);
//
//	    	    try {
//	    	        employeeService.employeeById(testEmployeeId);
//	    	        fail("Expected EmployeeNotFoundException to be thrown");
//	    	    } catch (EmployeeNotFoundException e) {
//	    	    	System.out.println("testDeleteEmployee: Employee not found after deletion, as expected.");
//	    	    }
	    	  employeeService.deleteEmployee(testEmployeeId);

	    	    // Now verify that the employee no longer exists
	    	    try {
	    	        employeeService.employeeById(testEmployeeId);
	    	        fail("Expected EmployeeNotFoundException to be thrown");
	    	    } catch (EmployeeNotFoundException e) {
	    	        // Expected exception
	    	        System.out.println("testDeleteEmployee: Employee not found after deletion, as expected.");
	    	    }
	    }

}
