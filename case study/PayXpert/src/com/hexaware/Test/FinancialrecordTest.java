package com.hexaware.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.FinancialRecordException;
import com.hexaware.interfaceimp.FinancialRecordServiceimp;
import com.hexaware.model.Financialrecord;

public class FinancialrecordTest {
	
	@Mock
	private Connection mockConn;
	@Mock
	private PreparedStatement mockPs;
	@Mock
	private ResultSet mockRs;

	@InjectMocks
	private FinancialRecordServiceimp financialRecordService; // Use the actual implementation

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		financialRecordService = new FinancialRecordServiceimp(mockConn); // Initialize
	}

	@Test
	public void testFinancialRecordServiceimp() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddFinancialRecord() throws SQLException, DatabaseConnectionException {
//		fail("Not yet implemented");
//		Financialrecord record = new Financialrecord(); //  set the fields
//
//		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
//		when(mockPs.executeUpdate()).thenReturn(1);
//
//		financialRecordService.extractFinancialRecord(record);
//
//		verify(mockConn).prepareStatement(anyString());
//		verify(mockPs).executeUpdate();
//		verify(mockPs).close();
		
		int employeeId = 1;
	    LocalDate recordDate = LocalDate.now();
	    String description = "Salary";
	    BigDecimal amount = new BigDecimal("5000");
	    String recordType = "Income";

	    when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
	    when(mockPs.executeUpdate()).thenReturn(1);

	    financialRecordService.addFinancialRecord(employeeId, recordDate, description, amount, recordType);

	    verify(mockConn).prepareStatement(anyString());
	    verify(mockPs).setInt(1, employeeId);
	    verify(mockPs).setDate(2, Date.valueOf(recordDate));
	    verify(mockPs).setString(3, description);
	    verify(mockPs).setBigDecimal(4, amount);
	    verify(mockPs).setString(5, recordType);
	    verify(mockPs).executeUpdate();
	    verify(mockPs).close();
	}

	@Test
	public void testGetFinancialRecordById() throws SQLException, FinancialRecordException, DatabaseConnectionException {
//		fail("Not yet implemented");
//		int recordId = 1;
//		Financialrecord expectedRecord = new Financialrecord(); // set
//
//		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
//		when(mockPs.executeQuery()).thenReturn(mockRs);
//		when(mockRs.next()).thenReturn(true);
//		// mock
//		when(mockRs.getInt("record_id")).thenReturn(recordId);
//
//		Financialrecord actualRecord = financialRecordService.getFinancialRecordById(recordId);
//
//		assertEquals(expectedRecord.getFinancialRecordById(), actualRecord.getFinancialRecordById());
//		verify(mockConn).prepareStatement(anyString());
//		verify(mockPs).executeQuery();
//		verify(mockPs).close();
//		verify(mockRs).close();
		  int recordId = 1;
		    Financialrecord expectedRecord = new Financialrecord(
		        1, 101, LocalDate.of(2024, 1, 1), "Salary", new BigDecimal("10000.00"), "Income"
		    );

		    when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		    when(mockPs.executeQuery()).thenReturn(mockRs);
		    when(mockRs.next()).thenReturn(true);
		    when(mockRs.getInt("record_id")).thenReturn(expectedRecord.getRecordID());
		    when(mockRs.getInt("employee_id")).thenReturn(expectedRecord.getEmployeeID());
		    when(mockRs.getDate("record_date")).thenReturn(Date.valueOf(expectedRecord.getRecordDate()));
		    when(mockRs.getString("description")).thenReturn(expectedRecord.getDescription());
		    when(mockRs.getBigDecimal("amount")).thenReturn(expectedRecord.getAmount());
		    when(mockRs.getString("record_type")).thenReturn(expectedRecord.getRecordType());

		    Financialrecord actualRecord = financialRecordService.getFinancialRecordById(recordId);

		    assertEquals(expectedRecord.getRecordID(), actualRecord.getRecordID());
		    assertEquals(expectedRecord.getEmployeeID(), actualRecord.getEmployeeID());
		    assertEquals(expectedRecord.getRecordDate(), actualRecord.getRecordDate());
		    assertEquals(expectedRecord.getDescription(), actualRecord.getDescription());
		    assertEquals(expectedRecord.getAmount(), actualRecord.getAmount());
		    assertEquals(expectedRecord.getRecordType(), actualRecord.getRecordType());

		    verify(mockConn).prepareStatement(anyString());
		    verify(mockPs).executeQuery();
		    verify(mockPs).close();
		    verify(mockRs).close();
		
	}

	@Test
	public void testGetFinancialRecordsForEmployee() throws SQLException, FinancialRecordException, DatabaseConnectionException {
		fail("Not yet implemented");
		int employeeId = 101;
		List<Financialrecord> expectedRecords = new ArrayList<>();
		Financialrecord record1 = new Financialrecord(); //  set
		Financialrecord record2 = new Financialrecord(); // set
		expectedRecords.add(record1);
		expectedRecords.add(record2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false);
		// mock
		when(mockRs.getInt("record_id")).thenReturn(1, 2);

		List<Financialrecord> actualRecords = financialRecordService.getFinancialRecordsForEmployee(employeeId);

		assertEquals(expectedRecords.size(), actualRecords.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetFinancialRecordsForDate() throws SQLException, FinancialRecordException, DatabaseConnectionException {
		fail("Not yet implemented");
		LocalDate date = LocalDate.of(2024, 1, 15);
		List<Financialrecord> expectedRecords = new ArrayList<>();
		Financialrecord record1 = new Financialrecord();  //set
		Financialrecord record2 = new Financialrecord();  //set
		expectedRecords.add(record1);
		expectedRecords.add(record2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		//when(mockPs.setDate(1, java.sql.Date.valueOf(date))).thenReturn(null);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false);
		//mock
		when(mockRs.getInt("record_id")).thenReturn(1,2);

		List<Financialrecord> actualRecords = financialRecordService.getFinancialRecordsForDate(date);

		assertEquals(expectedRecords.size(), actualRecords.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).setDate(1, java.sql.Date.valueOf(date));
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetFinancialRecordsForType() throws SQLException, FinancialRecordException, DatabaseConnectionException {
		fail("Not yet implemented");
		String type = "Income";
		List<Financialrecord> expectedRecords = new ArrayList<>();
		Financialrecord record1 = new Financialrecord(); // set
		Financialrecord record2 = new Financialrecord(); // set
		expectedRecords.add(record1);
		expectedRecords.add(record2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		//when(mockPs.setString(1, type)).thenReturn(null);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false);
		//mock
		when(mockRs.getInt("record_id")).thenReturn(1,2);

		List<Financialrecord> actualRecords = financialRecordService.getFinancialRecordsForType(type);

		assertEquals(expectedRecords.size(), actualRecords.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).setString(1, type);
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetTotalIncomeForEmployee() throws SQLException, FinancialRecordException, DatabaseConnectionException {
//		fail("Not yet implemented");
//		int employeeId = 101;
//		double expectedTotalIncome = 10000.0;
//
//		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
//		//when(mockPs.setInt(1, employeeId)).thenReturn(null);
//		when(mockPs.executeQuery()).thenReturn(mockRs);
//		when(mockRs.next()).thenReturn(true);
//		when(mockRs.getDouble(1)).thenReturn(expectedTotalIncome);
//
//		double actualTotalIncome = financialRecordService.getTotalIncomeForEmployee(employeeId);
//
//		assertEquals(expectedTotalIncome, actualTotalIncome, 0.001);
//		verify(mockConn).prepareStatement(anyString());
//		verify(mockPs).setInt(1, employeeId);
//		verify(mockPs).executeQuery();
//		verify(mockRs).next();
//		verify(mockRs).getDouble(1);
//		verify(mockPs).close();
//		verify(mockRs).close();
		
	    int employeeId = 101;
	    BigDecimal expectedTotalIncome = new BigDecimal("10000.00");

	    when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
	    when(mockPs.executeQuery()).thenReturn(mockRs);
	    when(mockRs.next()).thenReturn(true);
	    when(mockRs.getBigDecimal("total_income")).thenReturn(expectedTotalIncome);

	    BigDecimal actualTotalIncome = financialRecordService.getTotalIncomeForEmployee(employeeId);

	    assertEquals(expectedTotalIncome, actualTotalIncome);
	    verify(mockConn).prepareStatement(anyString());
	    verify(mockPs).setInt(1, employeeId);
	    verify(mockPs).executeQuery();
	    verify(mockRs).next();
	    verify(mockPs).close();
	    verify(mockRs).close();
	}

	@Test
	public void testGetTotalExpensesForEmployee() throws SQLException, FinancialRecordException, DatabaseConnectionException {
//		fail("Not yet implemented");
//		int employeeId = 101;
//		double expectedTotalExpenses = 5000.0;
//
//		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
//		//when(mockPs.setInt(1, employeeId)).thenReturn(null);
//		when(mockPs.executeQuery()).thenReturn(mockRs);
//		when(mockRs.next()).thenReturn(true);
//		when(mockRs.getDouble(1)).thenReturn(expectedTotalExpenses);
//
//		double actualTotalExpenses = financialRecordService.getTotalExpensesForEmployee(employeeId);
//
//		assertEquals(expectedTotalExpenses, actualTotalExpenses, 0.001);
//		verify(mockConn).prepareStatement(anyString());
//		verify(mockPs).setInt(1, employeeId);
//		verify(mockPs).executeQuery();
//		verify(mockRs).next();
//		verify(mockRs).getDouble(1);
//		verify(mockPs).close();
//		verify(mockRs).close();
		
		int employeeId = 101;
	    BigDecimal expectedTotalExpenses = new BigDecimal("5000.00");

	    when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
	    when(mockPs.executeQuery()).thenReturn(mockRs);
	    when(mockRs.next()).thenReturn(true);
	    when(mockRs.getBigDecimal("total_expenses")).thenReturn(expectedTotalExpenses);

	    BigDecimal actualTotalExpenses = financialRecordService.getTotalExpensesForEmployee(employeeId);

	    assertEquals(expectedTotalExpenses, actualTotalExpenses);
	    verify(mockConn).prepareStatement(anyString());
	    verify(mockPs).setInt(1, employeeId);
	    verify(mockPs).executeQuery();
	    verify(mockRs).next();
	    verify(mockPs).close();
	    verify(mockRs).close();
	}

}
