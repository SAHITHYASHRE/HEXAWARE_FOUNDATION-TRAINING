package com.hexaware.interfaceimp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.dao.IEmployeeService;
import com.hexaware.model.Employee;
import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.exception.InvalidInputException;
import com.hexaware.exception.ValidationService;
import com.hexaware.util.DbconnUtil;

public class EmployeeServiceimp implements IEmployeeService{
	
	 

	  
	 private final ValidationService validationService = new ValidationService();
	  
	public EmployeeServiceimp() {
        super();
    }

    @Override
    public void addEmployee(Employee emp) throws SQLException, DatabaseConnectionException {

    	try {
            validationService.validateEmployeeData(emp);
            validationService.validateDateNotFuture(emp.getDateOfBirth(), "Date of birth");
            validationService.isValidEmail(emp.getEmail());
            validationService.isValidPhoneNumber(emp.getPhoneNumber());
            validationService.isValidPosition(emp.getPosition());

            String sql = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DbconnUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                setEmployeeParams(ps, emp);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseConnectionException("Error adding employee to the database: " + e.getMessage(), e);
            }
        } catch (InvalidInputException e) {
            throw new SQLException(e.getMessage()); // Re-throw as SQLException for service layer
        }
    }

    @Override
    public List<Employee> viewEmployee() throws SQLException, DatabaseConnectionException {

    	
    	   List<Employee> list = new ArrayList<>();
           String sql = "SELECT * FROM employee";
           try (Connection conn = DbconnUtil.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
               while (rs.next()) {
                   list.add(extractEmployee(rs));
               }
           } catch (SQLException e) {
               throw new DatabaseConnectionException("Error viewing employees from the database: " + e.getMessage(), e);
           }
           return list;
    }

    @Override
    public boolean updateEmployee(Employee emp) throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {

        String sql = "UPDATE Employee SET " +
                "first_name = ?, last_name = ?, dob = ?, gender = ?, " +
                "email = ?, phone_number = ?, address = ?, position = ?, " +
                "doj = ?, termination_date = ? " +
                "WHERE employee_id = ?";

   try (Connection conn = DbconnUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

       stmt.setString(1, emp.getFirstName());
       stmt.setString(2, emp.getLastName());
       stmt.setDate(3, Date.valueOf(emp.getDateOfBirth())); // assuming LocalDate
       stmt.setString(4, emp.getGender());
       stmt.setString(5, emp.getEmail());
       stmt.setLong(6, emp.getPhoneNumber());
       stmt.setString(7, emp.getAddress());
       stmt.setString(8, emp.getPosition());
       stmt.setDate(9, Date.valueOf(emp.getJoiningDate()));
       
       // handle nullable terminationDate
       if (emp.getTerminationDate() != null) {
           stmt.setDate(10, Date.valueOf(emp.getTerminationDate()));
       } else {
           stmt.setNull(10, Types.DATE);
       }

       stmt.setInt(11, emp.getEmployeeID(0)); // ✅ Very important for WHERE clause

       int rowsUpdated = stmt.executeUpdate();
       return rowsUpdated > 0;

   } catch (SQLException e) {
       e.printStackTrace();
       return false;
   }
    }

    @Override
    public void deleteEmployee(int employeeId) throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {

    	
    	  String sql = "DELETE FROM employee WHERE employee_id = ?";
          try {
			try (Connection conn = DbconnUtil.getConnection();
			       PreparedStatement ps = conn.prepareStatement(sql)) {
			      ps.setInt(1, employeeId);
			      int rowsDeleted = ps.executeUpdate();
			      if (rowsDeleted == 0) {
			          throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found for deletion.");
			      }
			  } catch (SQLException e) {
			      throw new DatabaseConnectionException("Error deleting employee from the database: " + e.getMessage(), e);
			  }
		} catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void ShowConnection() {
        try (Connection conn = DbconnUtil.getConnection()) { // Use try-with-resources here
            System.out.println("Connection Established : " + conn);
        } catch (SQLException e) {
            System.err.println("Error showing connection: " + e.getMessage());
            e.printStackTrace(); // Consider logging the error
        }
    }


    @Override
    public Employee employeeById(int employeeId) throws SQLException, EmployeeNotFoundException, DatabaseConnectionException {

    	
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        Employee employee = null;
        try {
			try (Connection conn = DbconnUtil.getConnection();
			     PreparedStatement ps = conn.prepareStatement(sql)) {
			    ps.setInt(1, employeeId);
			    try (ResultSet rs = ps.executeQuery()) {
			        if (rs.next()) {
			            employee = extractEmployee(rs);
			        } else {
			            throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
			        }
			    }
			} catch (SQLException e) {
			    throw new DatabaseConnectionException("Error retrieving employee by ID from the database: " + e.getMessage(), e);
			}
		} catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return employee;
    }

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("employee_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("dob").toLocalDate(),
                rs.getString("gender"),
                rs.getString("email"),
                rs.getLong("phone_number"),
                rs.getString("address"),
                rs.getString("position"),
                rs.getDate("doj").toLocalDate(),
                rs.getDate("termination_date") != null ? rs.getDate("termination_date").toLocalDate() : null
        );
    }

    private void setEmployeeParams(PreparedStatement ps, Employee emp) throws SQLException {
        ps.setInt(1, emp.employeeById());
        ps.setString(2, emp.getFirstName());
        ps.setString(3, emp.getLastName());
        ps.setDate(4, Date.valueOf(emp.getDateOfBirth()));
        ps.setString(5, emp.getGender());
        ps.setString(6, emp.getEmail());
        ps.setLong(7, emp.getPhoneNumber());
        ps.setString(8, emp.getAddress());
        ps.setString(9, emp.getPosition());
        ps.setDate(10, Date.valueOf(emp.getJoiningDate()));
        if (emp.getTerminationDate() != null) {
            ps.setDate(11, Date.valueOf(emp.getTerminationDate()));
        } else {
            ps.setNull(11, Types.DATE);
        }
    }
	
	
	

}
