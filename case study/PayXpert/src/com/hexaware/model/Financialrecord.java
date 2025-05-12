package com.hexaware.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Financialrecord {
	
	 private int RecordID; 
	 private int EmployeeID; 
	 private LocalDate RecordDate; 
	 private String Description; 
	 private BigDecimal Amount; 
	 private String RecordType;
	 
	 
	public int getRecordID() {
		return RecordID;
	}
	public void setRecordID(int recordID) {
		RecordID = recordID;
	}
	public int getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}
	public LocalDate getRecordDate() {
		return RecordDate;
	}
	public void setRecordDate(LocalDate recordDate) {
		RecordDate = recordDate;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public BigDecimal getAmount() {
		return Amount;
	}
	public void setAmount(BigDecimal amount) {
		Amount = amount;
	}
	public String getRecordType() {
		return RecordType;
	}
	public void setRecordType(String recordType) {
		RecordType = recordType;
	}
	public Financialrecord(int recordID, int employeeID, LocalDate recordDate, String description, BigDecimal amount,
			String recordType) {
		super();
		this.RecordID = recordID;
		this.EmployeeID = employeeID;
		this.RecordDate = recordDate;
		this.Description = description;
		this.Amount = amount;
		this.RecordType = recordType;
	}
	public Financialrecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Financialrecord [RecordID=" + RecordID + ", EmployeeID=" + EmployeeID + ", RecordDate=" + RecordDate
				+ ", Description=" + Description + ", Amount=" + Amount + ", RecordType=" + RecordType + "]";
	} 
	 
	 

}
