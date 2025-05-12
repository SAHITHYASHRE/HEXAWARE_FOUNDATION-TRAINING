package com.hexaware.interfaceimp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.dao.IPayrollService;
import com.hexaware.model.Payroll;
import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.InvalidInputException;
import com.hexaware.exception.PayrollGenerationException;
import com.hexaware.exception.ValidationService;
import com.hexaware.util.DbconnUtil;

public class PayrollServiceimp implements IPayrollService {
	
	

	 private final ValidationService validationService = new ValidationService();
	
    public PayrollServiceimp(Connection mockConn) {
        super();
    }

    @Override
    public void insertPayroll(Payroll payroll) throws SQLException, DatabaseConnectionException {

    	try {
    	    validationService.validatePayrollData(payroll);
    	    validationService.validateDateNotFuture(payroll.getPayPeriodStartDate(), "Pay period start date");
    	    validationService.validateDateNotFuture(payroll.getPayPeriodEndDate(), "Pay period end date");

    	    String query = "INSERT INTO payroll (payroll_id, employee_id, PayPeriodStartDate, PayPeriodEndDate, basic_salary, overtime_pay, deductions, net_salary) "
    	                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    	    try (Connection conn = DbconnUtil.getConnection();
    	         PreparedStatement ps = conn.prepareStatement(query)) {

    	        ps.setInt(1, payroll.getPayrollID());
    	        ps.setInt(2, payroll.getEmployeeID());
    	        ps.setDate(3, Date.valueOf(payroll.getPayPeriodStartDate()));
    	        ps.setDate(4, Date.valueOf(payroll.getPayPeriodEndDate()));
    	        ps.setBigDecimal(5, payroll.getBasicSalary());
    	        ps.setBigDecimal(6, payroll.getOvertimePay());
    	        ps.setBigDecimal(7, payroll.getDeductions());

    	        // Ensure net salary is calculated before insertion
    	        payroll.calculateNetSalary();
    	        ps.setBigDecimal(8, payroll.getNetSalary());

    	        ps.executeUpdate();
    	    } catch (SQLException e) {
    	        throw new DatabaseConnectionException("Error inserting payroll data: " + e.getMessage(), e);
    	    }
    	} catch (InvalidInputException e) {
    	    throw new SQLException(e.getMessage()); // Re-throw as SQLException for service layer
    	}

    }

    @Override
    public List<Payroll> generatePayroll(int employeeId, LocalDate periodStart, LocalDate periodEnd) throws SQLException, DatabaseConnectionException {

    	
    	String query = "SELECT * FROM payroll";
        List<Payroll> payrollList = new ArrayList<>();
        try (Connection conn = DbconnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                payrollList.add(extractPayroll(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving all payroll data: " + e.getMessage(), e);
        }
        return payrollList;
    }

    @Override
    public Payroll getPayrollById(int payrollId) throws SQLException, PayrollGenerationException, DatabaseConnectionException {

    	
        String query = "SELECT * FROM payroll WHERE payroll_id = ?";
        Payroll payroll = null;
        try (Connection conn = DbconnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, payrollId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    payroll = extractPayroll(rs);
                } else {
                    throw new PayrollGenerationException("Payroll with ID " + payrollId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error retrieving payroll by ID: " + e.getMessage(), e);
        }
        return payroll;
    }

    @Override
    public List<Payroll> getPayrollsForEmployee(int employeeid) throws SQLException, PayrollGenerationException, DatabaseConnectionException {

    	
    	  String query = "SELECT * FROM payroll WHERE employee_id = ?";
          List<Payroll> list = new ArrayList<>();
          try (Connection conn = DbconnUtil.getConnection();
               PreparedStatement ps = conn.prepareStatement(query)) {
              ps.setInt(1, employeeid);
              try (ResultSet rs = ps.executeQuery()) {
                  while (rs.next()) {
                      list.add(extractPayroll(rs));
                  }
                  if (list.isEmpty()) {
                      // Consider if EmployeeNotFoundException is more appropriate here
                      // if you expect the employee to exist.
                      throw new PayrollGenerationException("No payroll records found for employee ID: " + employeeid);
                  }
              }
          } catch (SQLException e) {
              throw new DatabaseConnectionException("Error retrieving payrolls for employee: " + e.getMessage(), e);
          }
          return list;
    }

    @Override
    public List<Payroll> getPayrollsForPeriod(LocalDate periodStart, LocalDate periodEnd) throws SQLException, PayrollGenerationException, DatabaseConnectionException {

    	
    	   String query = "SELECT * FROM payroll WHERE PayPeriodStartDate >= ? AND PayPeriodEndDate <= ?";
           List<Payroll> list = new ArrayList<>();
           try (Connection conn = DbconnUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {
               ps.setDate(1, Date.valueOf(periodStart));
               ps.setDate(2, Date.valueOf(periodEnd));
               try (ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                       list.add(extractPayroll(rs));
                   }
                   if (list.isEmpty()) {
                       throw new PayrollGenerationException("No payroll records found for the period: " + periodStart + " to " + periodEnd);
                   }
               }
           } catch (SQLException e) {
               throw new DatabaseConnectionException("Error retrieving payrolls for period: " + e.getMessage(), e);
           }
           return list;
    }

    private Payroll extractPayroll(ResultSet rs) throws SQLException {
        return new Payroll(
                rs.getInt("payroll_id"),
                rs.getInt("employee_id"),
                rs.getDate("PayPeriodStartDate").toLocalDate(),
                rs.getDate("PayPeriodEndDate").toLocalDate(),
                rs.getBigDecimal("basic_salary"),
                rs.getBigDecimal("overtime_pay"),
                rs.getBigDecimal("deductions"),
                rs.getBigDecimal("net_salary")
        );
    }

	@Override
	public List<Payroll> generatePayroll() throws SQLException, DatabaseConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payroll> getPayrollsForPeriod(String startDate, String endDate)
			throws SQLException, PayrollGenerationException, DatabaseConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

}
