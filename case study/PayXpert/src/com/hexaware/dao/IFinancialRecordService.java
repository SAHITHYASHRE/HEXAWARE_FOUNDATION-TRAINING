package com.hexaware.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.FinancialRecordException;
import com.hexaware.model.Financialrecord;

public interface IFinancialRecordService {
	
	 void addFinancialRecord(int employeeId, LocalDate recordDate, String description, BigDecimal amount, String recordType) throws SQLException, DatabaseConnectionException;

	    Financialrecord getFinancialRecordById(int recordId) throws SQLException, FinancialRecordException, DatabaseConnectionException;

	    List<Financialrecord> getFinancialRecordsForEmployee(int employeeId) throws SQLException, FinancialRecordException, DatabaseConnectionException;

	    List<Financialrecord> getFinancialRecordsForDate(LocalDate recordDate) throws SQLException, FinancialRecordException, DatabaseConnectionException;

	    List<Financialrecord> getFinancialRecordsForType(String recordType) throws SQLException, FinancialRecordException, DatabaseConnectionException; // Added for report generation
	    
	    BigDecimal getTotalIncomeForEmployee(int employeeId) throws SQLException, FinancialRecordException, DatabaseConnectionException;
	    
	    BigDecimal getTotalExpensesForEmployee(int employeeId) throws SQLException, FinancialRecordException, DatabaseConnectionException;
	
	

}
