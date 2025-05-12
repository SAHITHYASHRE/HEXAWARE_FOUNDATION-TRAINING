package com.hexaware.interfaceimp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.dao.ITaxService;
import com.hexaware.model.Tax;
import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.InvalidInputException;
import com.hexaware.exception.TaxCalculationException;
import com.hexaware.exception.ValidationService;
import com.hexaware.util.DbconnUtil;

public class TaxServiceimp implements ITaxService {
	
	private final ValidationService validationService = new ValidationService();
	
	 public TaxServiceimp(Connection mockConn) {
	        super();
	    }

	    @Override
	    public BigDecimal CalculateTax(int employeeId, int taxYear) throws SQLException, TaxCalculationException {
	       
	    	

	    	
	    	try {
	            // Potentially validate employeeId and taxYear if needed
	            if (employeeId <= 0 || taxYear <= 0 || taxYear > java.time.Year.now().getValue()) {
	                throw new InvalidInputException("Invalid employee ID or tax year for tax calculation.");
	            }

	            BigDecimal taxableIncome = getTaxableIncome(employeeId, taxYear);
	            if (taxableIncome == null) {
	                return BigDecimal.ZERO;
	            }

	            BigDecimal taxAmount = BigDecimal.ZERO;
	            BigDecimal bracket1 = new BigDecimal("10000");
	            BigDecimal bracket2 = new BigDecimal("40000");
	            BigDecimal bracket3 = new BigDecimal("100000");
	            BigDecimal rate1 = new BigDecimal("0.10"); // 10%
	            BigDecimal rate2 = new BigDecimal("0.20"); // 20%
	            BigDecimal rate3 = new BigDecimal("0.30"); // 30%
	            BigDecimal rate4 = new BigDecimal("0.40"); // 40%

	            if (taxableIncome.compareTo(bracket1) <= 0) {
	                taxAmount = taxableIncome.multiply(rate1);
	            } else if (taxableIncome.compareTo(bracket2) <= 0) {
	                BigDecimal amount1 = bracket1.multiply(rate1);
	                BigDecimal amount2 = taxableIncome.subtract(bracket1).multiply(rate2);
	                taxAmount = amount1.add(amount2);
	            } else if (taxableIncome.compareTo(bracket3) <= 0) {
	                BigDecimal amount1 = bracket1.multiply(rate1);
	                BigDecimal amount2 = bracket2.subtract(bracket1).multiply(rate2);
	                BigDecimal amount3 = taxableIncome.subtract(bracket2).multiply(rate3);
	                taxAmount = amount1.add(amount2).add(amount3);
	            } else {
	                BigDecimal amount1 = bracket1.multiply(rate1);
	                BigDecimal amount2 = bracket2.subtract(bracket1).multiply(rate2);
	                BigDecimal amount3 = bracket3.subtract(bracket2).multiply(rate3);
	                BigDecimal amount4 = taxableIncome.subtract(bracket3).multiply(rate4);
	                taxAmount = amount1.add(amount2).add(amount3).add(amount4);
	            }
	            return taxAmount;

	        } catch (InvalidInputException e) {
	            throw new SQLException(e.getMessage()); // Re-throw as SQLException for service layer
	        } catch (Exception e) {
	            throw new TaxCalculationException("Error during tax calculation: " + e.getMessage(), e);
	        }
	    	
	        }


	    

	    public BigDecimal getTaxableIncome(int employeeId, int taxYear) throws SQLException, DatabaseConnectionException {

	    	
	    	   Connection conn = null;
	           PreparedStatement ps = null;
	           ResultSet rs = null;
	           BigDecimal taxableIncome = null;
	           try {
	               conn = DbconnUtil.getConnection();
	               String sql = "SELECT SUM(net_salary) AS taxable_income FROM payroll WHERE employee_id = ? AND YEAR(PayPeriodStartDate) = ?";
	               ps = conn.prepareStatement(sql);
	               ps.setInt(1, employeeId);
	               ps.setInt(2, taxYear);
	               rs = ps.executeQuery();
	               if (rs.next()) {
	                   taxableIncome = rs.getBigDecimal("taxable_income");
	               }
	           } catch (SQLException e) {
	               throw new DatabaseConnectionException("Error fetching taxable income: " + e.getMessage(), e);
	           } finally {
	               DbconnUtil.closeConnection(conn);
	               if (ps != null) ps.close();
	               if (rs != null) rs.close();
	           }
	           return taxableIncome;
	    }
	    @Override
	    public Tax getTaxById(int taxId) throws SQLException, TaxCalculationException, DatabaseConnectionException {

	    	
	    	Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        Tax tax = null;
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT * FROM tax WHERE tax_id = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, taxId);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                tax = extractTax(rs);
	            } else {
	                throw new TaxCalculationException("Tax record with ID " + taxId + " not found.");
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving tax by ID: " + e.getMessage(), e);
	        } finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) ps.close();
	            if (rs != null) rs.close();
	        }
	        return tax;
	    }

	    @Override
	    public List<Tax> getTaxForEmployee(int employeeId) throws SQLException, TaxCalculationException, DatabaseConnectionException {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        List<Tax> taxes = new ArrayList<>();
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT * FROM tax WHERE employee_id = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, employeeId);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                taxes.add(extractTax(rs));
	            }
	            if (taxes.isEmpty()) {
	                // Consider if EmployeeNotFoundException might be more appropriate
	                // if you expect the employee to have tax records.
	                throw new TaxCalculationException("No tax records found for employee ID: " + employeeId);
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving tax records for employee: " + e.getMessage(), e);
	        } finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) {
	                ps.close();
	            }
	            if (rs != null) {
	                rs.close();
	            }
	        }
	        return taxes;
	    }

	    @Override
	    public List<Tax> getTaxesForYear(int taxYear) throws SQLException, TaxCalculationException, DatabaseConnectionException {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        List<Tax> taxes = new ArrayList<>();
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT * FROM tax WHERE tax_year = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, taxYear);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                taxes.add(extractTax(rs));
	            }
	            if (taxes.isEmpty()) {
	                throw new TaxCalculationException("No tax records found for the year: " + taxYear);
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving tax records for year: " + e.getMessage(), e);
	        }
	         finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) {
	                ps.close();
	            }
	            if (rs != null) {
	                rs.close();
	            }
	        }
	        return taxes;
	    }

	    private Tax extractTax(ResultSet rs) throws SQLException {
	        return new Tax(
	                rs.getInt("tax_id"),
	                rs.getInt("employee_id"),
	                rs.getInt("tax_year"),
	                rs.getBigDecimal("taxable_income"),
	                rs.getBigDecimal("tax_amt")
	        );
	    }

	    public void insertTax(Tax tax) throws SQLException, DatabaseConnectionException {

	    	 try {
	             validationService.validateTaxData(tax);

	             Connection conn = null;
	             PreparedStatement ps = null;
	             try {
	                 conn = DbconnUtil.getConnection();
	                 String sql = "INSERT INTO tax (employee_id, tax_year, taxable_income, tax_amt) VALUES (?, ?, ?, ?)";
	                 ps = conn.prepareStatement(sql);
	                 ps.setInt(1, tax.getEmployeeID());
	                 ps.setInt(2, tax.getTaxYear());
	                 ps.setBigDecimal(3, tax.getTaxableIncome());
	                 ps.setBigDecimal(4, tax.getTaxAmount());
	                 ps.executeUpdate();
	             } catch (SQLException e) {
	                 throw new DatabaseConnectionException("Error inserting tax data: " + e.getMessage(), e);
	             } finally {
	                 DbconnUtil.closeConnection(conn);
	                 if (ps != null) ps.close();
	             }
	         } catch (InvalidInputException e) {
	             throw new SQLException(e.getMessage()); // Re-throw as SQLException for service layer
	         }
	    	
	    }


}
