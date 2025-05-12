package com.hexaware.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.TaxCalculationException;
import com.hexaware.interfaceimp.TaxServiceimp;
import com.hexaware.model.Tax;

public class TaxTest {
	

	@Mock
	private Connection mockConn;
	@Mock
	private PreparedStatement mockPs;
	@Mock
	private ResultSet mockRs;

	@InjectMocks
	private TaxServiceimp taxService; // Inject the actual implementation

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		taxService = new TaxServiceimp(mockConn); // Initialize with the mock connection
	}

	@Test
	public void testTaxServiceimp() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateTax() throws SQLException, DatabaseConnectionException, TaxCalculationException {
//		fail("Not yet implemented");
//		double income = 50000.0;
//		int employeeId = 101;
//		double expectedTax = 10000.0; // Example
//
//		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
//		//when(mockPs.setDouble(1, income)).thenReturn(null);
//		//when(mockPs.setDouble(2, income)).thenReturn(null); // Set the parameter twice
//		when(mockPs.executeQuery()).thenReturn(mockRs);
//		when(mockRs.next()).thenReturn(true);
//		when(mockRs.getDouble("tax_rate")).thenReturn(0.20); // Example tax rate
//
//		double actualTax = taxService.CalculateTax(income, employeeId);
//
//		assertEquals(expectedTax, actualTax, 0.001); // Use a delta for double comparison.
//		verify(mockConn).prepareStatement(anyString());
//		verify(mockPs).setDouble(1, income);
//		verify(mockPs).setDouble(2, income);
//		verify(mockPs).executeQuery();
//		verify(mockRs).next();
//		verify(mockRs).getDouble("tax_rate");
//		verify(mockPs).close();
//		verify(mockRs).close();
		
		
		    int employeeId = 101;
		    int taxYear = 2024;
		    BigDecimal income = new BigDecimal("50000.0");
		    BigDecimal expectedTax = new BigDecimal("10000.0"); // Based on your logic

		    TaxServiceimp taxService = Mockito.spy(new TaxServiceimp(mockConn));
		    doReturn(income).when(taxService).getTaxableIncome(employeeId, taxYear);

		    BigDecimal actualTax = taxService.CalculateTax(employeeId, taxYear);

		    assertEquals(0, expectedTax.compareTo(actualTax)); // Compare BigDecimals properly
		}

	

	@Test
	public void testGetTaxableIncome() throws SQLException, DatabaseConnectionException {
//		fail("Not yet implemented");
//		int employeeId = 101;
//		LocalDate date = LocalDate.of(2024, 1, 15);
//		double expectedTaxableIncome = 40000.0;
//
//		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
//		//when(mockPs.setInt(1, employeeId)).thenReturn(null);
//		//when(mockPs.setDate(2, java.sql.Date.valueOf(date))).thenReturn(null);
//		when(mockPs.executeQuery()).thenReturn(mockRs);
//		when(mockRs.next()).thenReturn(true);
//		when(mockRs.getDouble("taxable_income")).thenReturn(expectedTaxableIncome);
//
//		double actualTaxableIncome = taxService.getTaxableIncome(employeeId, date);
//
//		assertEquals(expectedTaxableIncome, actualTaxableIncome, 0.001);
//		verify(mockConn).prepareStatement(anyString());
//		verify(mockPs).setInt(1, employeeId);
//		verify(mockPs).setDate(2, java.sql.Date.valueOf(date));
//		verify(mockPs).executeQuery();
//		verify(mockRs).next();
//		verify(mockRs).getDouble("taxable_income");
//		verify(mockPs).close();
//		verify(mockRs).close();
		
		int employeeId = 101;
	    LocalDate date = LocalDate.of(2024, 1, 15);
	    int taxYear = date.getYear(); // Extract taxYear
	    BigDecimal expectedTaxableIncome = new BigDecimal("40000.0");

	    when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
	    when(mockPs.executeQuery()).thenReturn(mockRs);
	    when(mockRs.next()).thenReturn(true);
	    when(mockRs.getBigDecimal("taxable_income")).thenReturn(expectedTaxableIncome);

	    TaxServiceimp taxService = new TaxServiceimp(mockConn);

	    BigDecimal actualTaxableIncome = taxService.getTaxableIncome(employeeId, taxYear);

	    assertEquals(0, expectedTaxableIncome.compareTo(actualTaxableIncome));
	    verify(mockConn).prepareStatement(anyString());
	    verify(mockPs).setInt(1, employeeId);
	    verify(mockPs).setInt(2, taxYear);
	    verify(mockPs).executeQuery();
	    verify(mockRs).next();
	    verify(mockRs).getBigDecimal("taxable_income");
	    verify(mockPs).close();
	    verify(mockRs).close();
	}

	@Test
	public void testGetTaxById() throws SQLException, TaxCalculationException, DatabaseConnectionException {
		fail("Not yet implemented");
		int taxId = 1;
		Tax expectedTax = new Tax(); //  set the fields

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("tax_id")).thenReturn(taxId);
		// Mock the result set

		Tax actualTax = taxService.getTaxById(taxId);

		assertEquals(expectedTax.getTaxID(), actualTax.getTaxID());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetTaxForEmployee() throws SQLException, TaxCalculationException, DatabaseConnectionException {
		fail("Not yet implemented");
		int employeeId = 101;
		List<Tax> expectedTaxes = new ArrayList<>();
		Tax tax1 = new Tax(); //  set fields
		Tax tax2 = new Tax();  // set fields
		expectedTaxes.add(tax1);
		expectedTaxes.add(tax2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("tax_id")).thenReturn(1,2);
		// Mock the result set

		List<Tax> actualTaxes = taxService.getTaxForEmployee(employeeId);

		assertEquals(expectedTaxes.size(), actualTaxes.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}

	@Test
	public void testGetTaxesForYear() throws SQLException, TaxCalculationException, DatabaseConnectionException {
		fail("Not yet implemented");
		int year = 2024;
		List<Tax> expectedTaxes = new ArrayList<>();
		Tax tax1 = new Tax();  // set fields
		Tax tax2 = new Tax(); // set fields
		expectedTaxes.add(tax1);
		expectedTaxes.add(tax2);

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeQuery()).thenReturn(mockRs);
		when(mockRs.next()).thenReturn(true, true, false);
        when(mockRs.getInt("tax_id")).thenReturn(1,2);
		// Mock the result set

		List<Tax> actualTaxes = taxService.getTaxesForYear(year);

		assertEquals(expectedTaxes.size(), actualTaxes.size());
		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeQuery();
		verify(mockPs).close();
		verify(mockRs).close();
	}
	

	@Test
	public void testInsertTax() throws SQLException, DatabaseConnectionException {
		fail("Not yet implemented");
		Tax tax = new Tax(); //  set the fields of the tax object

		when(mockConn.prepareStatement(anyString())).thenReturn(mockPs);
		when(mockPs.executeUpdate()).thenReturn(1);

		taxService.insertTax(tax);

		verify(mockConn).prepareStatement(anyString());
		verify(mockPs).executeUpdate();
		verify(mockPs).close();
	}

}
