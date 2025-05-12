package com.hexaware.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.interfaceimp.EmployeeServiceimp;
import com.hexaware.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeTest {
	
	@Mock
	private Connection mockConn;
	@Mock
	private PreparedStatement mockPs;
	@Mock
	private Statement mockStmt;
	@Mock
	private ResultSet mockRs;

	@InjectMocks
	private EmployeeServiceimp employeeService;

	@Test
	public void testEmployeeServiceimp() {
		fail("Not yet implemented");
	}
	
	@Before
	public void setUp() throws SQLException {
		//MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddEmployee() throws SQLException, DatabaseConnectionException {
//		//fail("Not yet implemented");
//		LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
//		LocalDate joiningDate = LocalDate.of(2023, 1, 1);
//		Employee employee = new Employee(1, "John", "Doe", dateOfBirth, "Male", "john.doe@example.com", 1234567890L,
//				"123 Main St", "Developer", joiningDate, null);
//
//		when(mockConn.prepareStatement(toString()))).thenReturn(mockPs);
//		when(mockPs.executeUpdate()).thenReturn(1);
//
//		employeeService.addEmployee(employee);
//
//		verify(mockConn).prepareStatement(toString());
//		verify(mockPs).executeUpdate();
//		verify(mockPs).close();
		LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
		LocalDate joiningDate = LocalDate.of(2023, 1, 1);
		Employee employee = new Employee(1, "John", "Doe", dateOfBirth, "Male", "john.doe@example.com", 1234567890L,
				"123 Main St", "Developer", joiningDate, null);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeUpdate()).thenReturn(1);

		employeeService.addEmployee(employee);

		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeUpdate();
		verify(mockPs).close();
	}

	



	@Test
	public void testViewEmployee() throws SQLException, DatabaseConnectionException {
		fail("Not yet implemented");
		List<Employee> expectedEmployees = new ArrayList<>();
		LocalDate dateOfBirth1 = LocalDate.of(1990, 5, 15);
		LocalDate joiningDate1 = LocalDate.of(2023, 1, 1);
		LocalDate dateOfBirth2 = LocalDate.of(1985, 10, 20);
		LocalDate joiningDate2 = LocalDate.of(2022, 8, 15);

		expectedEmployees.add(new Employee(1, "John", "Doe", dateOfBirth1, "Male", "john.doe@example.com", 1234567890L,
				"123 Main St", "Developer", joiningDate1, null));
		expectedEmployees.add(new Employee(2, "Jane", "Smith", dateOfBirth2, "Female", "jane.smith@example.com",
				9876543210L, "456 Oak Ave", "Manager", joiningDate2, null));

		when(mockConn.createStatement()).thenReturn(mockStmt);
		when(mockStmt.executeQuery(toString())).thenReturn(mockRs);

		when(mockRs.next()).thenReturn(true, true, false);
		when(mockRs.getInt("employee_id")).thenReturn(1, 2);
		when(mockRs.getString("first_name")).thenReturn("John", "Jane");
		when(mockRs.getString("last_name")).thenReturn("Doe", "Smith");
		when(mockRs.getDate("dob")).thenReturn(Date.valueOf(dateOfBirth1), Date.valueOf(dateOfBirth2));
		when(mockRs.getString("gender")).thenReturn("Male", "Female");
		when(mockRs.getString("email")).thenReturn("john.doe@example.com", "jane.smith@example.com");
		when(mockRs.getLong("phone_number")).thenReturn(1234567890L, 9876543210L);
		when(mockRs.getString("address")).thenReturn("123 Main St", "456 Oak Ave");
		when(mockRs.getString("position")).thenReturn("Developer", "Manager");
		when(mockRs.getDate("doj")).thenReturn(Date.valueOf(joiningDate1), Date.valueOf(joiningDate2));
		//when(mockRs.getDate("termination_date")).thenReturn(null, null);
		when(mockRs.getDate("termination_date")).thenReturn((Date) null, (Date) null);


		List<Employee> actualEmployees = employeeService.viewEmployee();

		assertEquals(expectedEmployees.size(), actualEmployees.size());
		assertEquals(expectedEmployees.get(0).getFirstName(), actualEmployees.get(0).getFirstName());
		assertEquals(expectedEmployees.get(1).getFirstName(), actualEmployees.get(1).getFirstName());
		verify(mockConn).createStatement();
		verify(mockStmt).executeQuery(toString());
		verify(mockStmt).close();
		verify(mockRs).close();
	}



	@Test
	public void testUpdateEmployee() throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {
		fail("Not yet implemented");
		LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
		LocalDate joiningDate = LocalDate.of(2023, 1, 1);
		Employee employee = new Employee(1, "John", "Doe", dateOfBirth, "Male", "john.doe@example.com", 1234567890L,
				"123 Main St", "Developer", joiningDate, null);

		when(mockConn.prepareStatement(toString())).thenReturn(mockPs);
		when(mockPs.executeUpdate()).thenReturn(1);
		//when(mockPs.setInt(11, employee.getEmployeeID())).thenReturn(1);
		doNothing().when(mockPs).setInt(11, employee.getEmployeeID(0));

		employeeService.updateEmployee(employee);

		verify(mockConn).prepareStatement(toString());
		verify(mockPs).executeUpdate();
		verify(mockPs).setInt(11, employee.getEmployeeID(0));
		verify(mockPs).close();
	}

	
	

	@Test
	public void testDeleteEmployee() throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {
		fail("Not yet implemented");
		int employeeId = 1;
		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeUpdate()).thenReturn(1);

		employeeService.deleteEmployee(employeeId);

		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeUpdate();
		verify(mockPs).setInt(1, employeeId);
		verify(mockPs).close();
	}

	@Test
	public void testEmployeeById() throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {
		fail("Not yet implemented");
		int employeeId = 1;
		LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);
		LocalDate joiningDate = LocalDate.of(2023, 1, 1);
		Employee expectedEmployee = new Employee(1, "John", "Doe", dateOfBirth, "Male", "john.doe@example.com",
				1234567890L, "123 Main St", "Developer", joiningDate, null);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true);
		when(mockRs.getInt("employee_id")).thenReturn(1);
		when(mockRs.getString("first_name")).thenReturn("John");
		when(mockRs.getString("last_name")).thenReturn("Doe");
		when(mockRs.getDate("dob")).thenReturn(Date.valueOf(dateOfBirth));
		when(mockRs.getString("gender")).thenReturn("Male");
		when(mockRs.getString("email")).thenReturn("john.doe@example.com");
		when(mockRs.getLong("phone_number")).thenReturn(1234567890L);
		when(mockRs.getString("address")).thenReturn("123 Main St");
		when(mockRs.getString("position")).thenReturn("Developer");
		when(mockRs.getDate("doj")).thenReturn(Date.valueOf(joiningDate));
		when(mockRs.getDate("termination_date")).thenReturn(null);

		Employee actualEmployee = employeeService.employeeById(employeeId);

		assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).setInt(1, employeeId);
		verify(mockRs).close();
		verify(mockPs).close();
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void testEmployeeByIdNotFound() throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {
		int employeeId = 1;
		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(false);

		employeeService.employeeById(employeeId);

		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).setInt(1, employeeId);
		verify(mockRs).close();
		verify(mockPs).close();
	}

}
