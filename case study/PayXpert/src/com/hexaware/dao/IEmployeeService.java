package com.hexaware.dao;

import java.sql.SQLException;
import java.util.List;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.model.Employee;

public interface IEmployeeService {
	
	public void addEmployee(Employee emp) throws SQLException, DatabaseConnectionException;
	public List<Employee> viewEmployee() throws SQLException, DatabaseConnectionException;
	public boolean updateEmployee(Employee employee) throws SQLException, EmployeeNotFoundException, DatabaseConnectionException;
	public void deleteEmployee(int employeeId) throws SQLException, EmployeeNotFoundException, DatabaseConnectionException;
	public Employee employeeById(int employeeId) throws SQLException, EmployeeNotFoundException, DatabaseConnectionException;
	public void ShowConnection();

	
	
}
