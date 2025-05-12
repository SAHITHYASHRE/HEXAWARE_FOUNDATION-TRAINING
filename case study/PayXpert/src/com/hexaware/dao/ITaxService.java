package com.hexaware.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.TaxCalculationException;
import com.hexaware.model.Tax;

public interface ITaxService {
	
	public BigDecimal CalculateTax(int employeeid,int taxYear) throws SQLException, TaxCalculationException;
	public Tax getTaxById(int taxId) throws SQLException, TaxCalculationException, DatabaseConnectionException;
	public List<Tax> getTaxForEmployee(int employeeid) throws SQLException, TaxCalculationException, DatabaseConnectionException;
	public List<Tax> getTaxesForYear(int taxYear) throws SQLException, TaxCalculationException, DatabaseConnectionException;

}
