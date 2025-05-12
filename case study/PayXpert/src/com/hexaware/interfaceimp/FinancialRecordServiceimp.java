package com.hexaware.interfaceimp;

import com.hexaware.dao.IFinancialRecordService;
import com.hexaware.model.Financialrecord;
import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.FinancialRecordException;
import com.hexaware.exception.InvalidInputException;
import com.hexaware.exception.ValidationService;
import com.hexaware.util.DbconnUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinancialRecordServiceimp implements IFinancialRecordService {
	
	private final ValidationService validationService = new ValidationService();
	 public FinancialRecordServiceimp(Connection mockConn) {
	        super();
	    }
	
	 
	 @Override
	    public void addFinancialRecord(int employeeId, LocalDate recordDate, String description, BigDecimal amount, String recordType) throws SQLException, DatabaseConnectionException {

		 try {
	            validationService.validateDateNotFuture(recordDate, "Record Date");
	            validationService.validateDescription(description);
	            validationService.validateAmount("Amount", amount);
	            validationService.validateRecordType(recordType);
	            validationService.validateEmployeeId(employeeId); // Basic check

	            Connection conn = null;
	            PreparedStatement ps = null;
	            try {
	                conn = DbconnUtil.getConnection();
	                String sql = "INSERT INTO financialrecord (employee_id, record_date, description, amount, record_type) VALUES (?, ?, ?, ?, ?)";
	                ps = conn.prepareStatement(sql);
	                ps.setInt(1, employeeId);
	                ps.setDate(2, Date.valueOf(recordDate));
	                ps.setString(3, description);
	                ps.setBigDecimal(4, amount);
	                ps.setString(5, recordType);
	                ps.executeUpdate();
	            } catch (SQLException e) {
	                throw new DatabaseConnectionException("Error adding financial record: " + e.getMessage(), e);
	            } finally {
	                DbconnUtil.closeConnection(conn);
	                if (ps != null) ps.close();
	            }
	        } catch (InvalidInputException e) {
	            throw new SQLException(e.getMessage()); // Re-throw for service layer
	        }
	    }

	    @Override
	    public Financialrecord getFinancialRecordById(int recordId) throws SQLException, FinancialRecordException, DatabaseConnectionException {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        Financialrecord record = null;
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT * FROM financialrecord WHERE record_id = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, recordId);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                record = extractFinancialRecord(rs);
	            }
	            else {
	                throw new FinancialRecordException("Financial record with ID " + recordId + " not found.");
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving financial record by ID: " + e.getMessage(), e);
	        }
	         finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) ps.close();
	            if (rs != null) rs.close();
	        }
	        return record;
	    }

	    @Override
	    public List<Financialrecord> getFinancialRecordsForEmployee(int employeeId) throws SQLException, FinancialRecordException, DatabaseConnectionException {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        List<Financialrecord> records = new ArrayList<>();
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT * FROM financialrecord WHERE employee_id IN (SELECT employee_id FROM employee WHERE employee_id = ?)";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, employeeId);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                records.add(extractFinancialRecord(rs));
	            }
	            if (records.isEmpty()) {
	                // Consider throwing EmployeeNotFoundException if you expect records for a valid employee
	                throw new FinancialRecordException("No financial records found for employee ID: " + employeeId);
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving financial records for employee: " + e.getMessage(), e);
	        } finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) ps.close();
	            if (rs != null) rs.close();
	        }
	        return records;
	    }

	    @Override
	    public List<Financialrecord> getFinancialRecordsForDate(LocalDate recordDate) throws SQLException, FinancialRecordException, DatabaseConnectionException {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        List<Financialrecord> records = new ArrayList<>();
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT * FROM financialrecord WHERE record_date = ?";
	            ps = conn.prepareStatement(sql);
	            ps.setDate(1, Date.valueOf(recordDate));
	            rs = ps.executeQuery();
	            while (rs.next()) {
	                records.add(extractFinancialRecord(rs));
	            }
	            if (records.isEmpty()) {
	                throw new FinancialRecordException("No financial records found for date: " + recordDate);
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving financial records for date: " + e.getMessage(), e);
	        } finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) ps.close();
	            if (rs != null) rs.close();
	        }
	        return records;
	    }

	    private Financialrecord extractFinancialRecord(ResultSet rs) throws SQLException {
	        return new Financialrecord(
	                rs.getInt("record_id"),
	                rs.getInt("employee_id"),
	                rs.getDate("record_date").toLocalDate(),
	                rs.getString("description"),
	                rs.getBigDecimal("amount"),
	                rs.getString("record_type")
	        );
	    }

	    @Override
	    public List<Financialrecord> getFinancialRecordsForType(String recordType) throws SQLException, FinancialRecordException, DatabaseConnectionException {

	    	
	    	try {
	            validationService.validateRecordType(recordType);
	            Connection conn = null;
	            PreparedStatement ps = null;
	            ResultSet rs = null;
	            List<Financialrecord> records = new ArrayList<>();
	            try {
	                conn = DbconnUtil.getConnection();
	                String sql = "SELECT * FROM financialrecord WHERE record_type = ?";
	                ps = conn.prepareStatement(sql);
	                ps.setString(1, recordType);
	                rs = ps.executeQuery();
	                while (rs.next()) {
	                    records.add(extractFinancialRecord(rs));
	                }
	                if (records.isEmpty()) {
	                    throw new FinancialRecordException("No financial records found for type: " + recordType);
	                }
	            } catch (SQLException e) {
	                throw new DatabaseConnectionException("Error retrieving financial records for type: " + e.getMessage(), e);
	            } finally {
	                DbconnUtil.closeConnection(conn);
	                if (ps != null) ps.close();
	                if (rs != null) rs.close();
	            }
	            return records;
	        } catch (InvalidInputException e) {
	            throw new SQLException(e.getMessage()); // Re-throw for service layer
	        }
	    }

	    @Override
	    public BigDecimal getTotalIncomeForEmployee(int employeeId) throws SQLException, FinancialRecordException, DatabaseConnectionException {

	    	
	    	 Connection conn = null;
	         PreparedStatement ps = null;
	         ResultSet rs = null;
	         BigDecimal totalIncome = BigDecimal.ZERO;
	         try {
	             conn = DbconnUtil.getConnection();
	             String sql = "SELECT SUM(fr.amount) AS total_income " +
	                          "FROM financialrecord fr " +
	                          "WHERE fr.employee_id = ? AND fr.record_type = 'Income'";
	             ps = conn.prepareStatement(sql);
	             ps.setInt(1, employeeId);
	             rs = ps.executeQuery();
	             if (rs.next()) {
	                 BigDecimal result = rs.getBigDecimal("total_income");
	                 totalIncome = (result != null) ? result : BigDecimal.ZERO;
	             } else {
	                 // Consider if EmployeeNotFoundException is appropriate
	                 throw new FinancialRecordException("Could not retrieve total income for employee ID: " + employeeId);
	             }
	         } catch (SQLException e) {
	             throw new DatabaseConnectionException("Error retrieving total income: " + e.getMessage(), e);
	         } finally {
	             DbconnUtil.closeConnection(conn);
	             if (ps != null) ps.close();
	             if (rs != null) rs.close();
	         }
	         return totalIncome;
	    }

	    @Override
	    public BigDecimal getTotalExpensesForEmployee(int employeeId) throws SQLException, FinancialRecordException, DatabaseConnectionException {

	    	
	    	Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        BigDecimal totalExpenses = BigDecimal.ZERO;
	        try {
	            conn = DbconnUtil.getConnection();
	            String sql = "SELECT SUM(fr.amount) AS total_expenses " +
	                         "FROM financialrecord fr " +
	                         "WHERE fr.employee_id = ? AND fr.record_type = 'Expense'";
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, employeeId);
	            rs = ps.executeQuery();
	            if (rs.next()) {
	                BigDecimal result = rs.getBigDecimal("total_expenses");
	                totalExpenses = (result != null) ? result : BigDecimal.ZERO;
	            } else {
	                // Consider if EmployeeNotFoundException is appropriate
	                throw new FinancialRecordException("Could not retrieve total expenses for employee ID: " + employeeId);
	            }
	        } catch (SQLException e) {
	            throw new DatabaseConnectionException("Error retrieving total expenses: " + e.getMessage(), e);
	        } finally {
	            DbconnUtil.closeConnection(conn);
	            if (ps != null) ps.close();
	            if (rs != null) rs.close();
	        }
	        return totalExpenses;
	    }
	    }
	


