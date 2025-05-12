package com.hexaware.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.PayrollGenerationException;
import com.hexaware.model.Payroll;

public interface IPayrollService {
	
	public void insertPayroll(Payroll payroll) throws SQLException, DatabaseConnectionException;
	public List<Payroll> generatePayroll() throws SQLException, DatabaseConnectionException;
	public Payroll getPayrollById(int payrollId) throws SQLException, PayrollGenerationException,DatabaseConnectionException ;
	public List<Payroll> getPayrollsForEmployee(int employeeid) throws SQLException, PayrollGenerationException, DatabaseConnectionException;
	public List<Payroll> getPayrollsForPeriod(String startDate, String endDate) throws SQLException, PayrollGenerationException, DatabaseConnectionException;
	List<Payroll> generatePayroll(int employeeId, LocalDate periodStart, LocalDate periodEnd)
			throws SQLException, DatabaseConnectionException;
	List<Payroll> getPayrollsForPeriod(LocalDate periodStart, LocalDate periodEnd)
			throws SQLException, PayrollGenerationException, DatabaseConnectionException;

}
