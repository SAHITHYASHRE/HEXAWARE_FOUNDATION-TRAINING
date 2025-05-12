package com.hexaware.model;

import java.math.BigDecimal;

public class Tax {
	
	 private int TaxID;
	 private int EmployeeID; 
	 private int TaxYear; 
	 private BigDecimal TaxableIncome;
	 private BigDecimal TaxAmount;

	 public int getTaxID() {
		return TaxID;
	}
	public void setTaxID(int taxID) {
		TaxID = taxID;
	}
	public int getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}
	public int getTaxYear() {
		return TaxYear;
	}
	public void setTaxYear(int taxYear) {
		TaxYear = taxYear;
	}
	public BigDecimal getTaxableIncome() {
		return TaxableIncome;
	}
	public void setTaxableIncome(BigDecimal taxableIncome) {
		TaxableIncome = taxableIncome;
	}
	public BigDecimal getTaxAmount() {
		return TaxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		TaxAmount = taxAmount;
	}
	
	public Tax(int taxID, int employeeID, int taxYear, BigDecimal taxableIncome, BigDecimal taxAmount) {
		super();
		this.TaxID = taxID;
		this.EmployeeID = employeeID;
		this.TaxYear = taxYear;
		this.TaxableIncome = taxableIncome;
		this.TaxAmount = taxAmount;
	}
	
	public Tax() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Tax [TaxID=" + TaxID + ", EmployeeID=" + EmployeeID + ", TaxYear=" + TaxYear + ", TaxableIncome="
				+ TaxableIncome + ", TaxAmount=" + TaxAmount + "]";
	}
	
	
	
	
}
