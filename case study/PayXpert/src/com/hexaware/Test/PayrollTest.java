package com.hexaware.Test;

import static org.junit.Assert.*;

import org.junit.Test;


import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import com.hexaware.model.Payroll;
import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.PayrollGenerationException;
import com.hexaware.interfaceimp.PayrollServiceimp;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PayrollTest {
	

	@Mock
	private Connection mockConn;
	@Mock
	private PreparedStatement mockPs;
	@Mock
	private ResultSet mockRs;

	@InjectMocks
	private PayrollServiceimp payrollService; // Use the *actual* implementation

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		payrollService = new PayrollServiceimp(mockConn); // Initialize with mock Conn
	}

	@Test
	public void testPayrollServiceimp() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertPayroll() throws SQLException, DatabaseConnectionException {
		fail("Not yet implemented");
		Payroll payroll = new Payroll(); // Create a sample payroll object.  You'll need to set its fields.

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeUpdate()).thenReturn(1);

		payrollService.insertPayroll(payroll);

		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeUpdate();
		verify(mockPs).close();
	}

	@Test
	public void testGeneratePayroll() throws SQLException, DatabaseConnectionException {
		fail("Not yet implemented");
		int employeeId = 1;
		LocalDate periodStart = LocalDate.of(2024, 1, 1);
		LocalDate periodEnd = LocalDate.of(2024, 1, 31);
		Payroll expectedPayroll = new Payroll(); //  set fields

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true);
		// Mock the ResultSet to return the expected payroll data.
		when(mockRs.getInt("payroll_id")).thenReturn(1); // Example
		// ... Mock other fields ...

		List<Payroll> actualPayroll = payrollService.generatePayroll(employeeId, periodStart, periodEnd);

		assertEquals(expectedPayroll.getPayrollID(), ((Payroll) actualPayroll).getPayrollID());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockRs).next();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetPayrollById() throws SQLException, PayrollGenerationException, DatabaseConnectionException {
		fail("Not yet implemented");
		int payrollId = 1;
		Payroll expectedPayroll = new Payroll(); //  set fields

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true);
		when(mockRs.getInt("payroll_id")).thenReturn(payrollId);
		//mock result set

		Payroll actualPayroll = payrollService.getPayrollById(payrollId);

		assertEquals(expectedPayroll.getPayrollID(), actualPayroll.getPayrollID());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetPayrollsForEmployee() throws SQLException, PayrollGenerationException, DatabaseConnectionException {
		fail("Not yet implemented");
		int employeeId = 1;
		List<Payroll> expectedPayrolls = new ArrayList<>();
		Payroll payroll1 = new Payroll();  //set fields
		Payroll payroll2 = new Payroll(); // set fields
		expectedPayrolls.add(payroll1);
		expectedPayrolls.add(payroll2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false); // Simulate 2 payrolls
		//mock result set
		when(mockRs.getInt("payroll_id")).thenReturn(1,2);

		List<Payroll> actualPayrolls = payrollService.getPayrollsForEmployee(employeeId);

		assertEquals(expectedPayrolls.size(), actualPayrolls.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetPayrollsForPeriod() throws SQLException, PayrollGenerationException, DatabaseConnectionException {
		fail("Not yet implemented");
		LocalDate periodStart = LocalDate.of(2024, 1, 1);
		LocalDate periodEnd = LocalDate.of(2024, 1, 31);
		List<Payroll> expectedPayrolls = new ArrayList<>();
		Payroll payroll1 = new Payroll(); // set
		Payroll payroll2 = new Payroll();
		expectedPayrolls.add(payroll1);
		expectedPayrolls.add(payroll2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false);
		//mock result set
		when(mockRs.getInt("payroll_id")).thenReturn(1,2);

		List<Payroll> actualPayrolls = payrollService.getPayrollsForPeriod(periodStart, periodEnd);

		assertEquals(expectedPayrolls.size(), actualPayrolls.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

}
