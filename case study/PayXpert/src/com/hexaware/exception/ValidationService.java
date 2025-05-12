package com.hexaware.exception;

import com.hexaware.model.Employee;
import com.hexaware.model.Payroll;
import com.hexaware.model.Tax;
import com.hexaware.model.Financialrecord;
import java.time.LocalDate;
import java.time.Year;
import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public class ValidationService {

	    /**
	     * Validates employee data.
	     *
	     * @param employee The employee object to validate.
	     * @throws InvalidInputException If any of the employee data is invalid.
	     */
	    public void validateEmployeeData(Employee employee) throws InvalidInputException {
	        if (employee == null) {
	            throw new InvalidInputException("Employee object cannot be null.");
	        }
	        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
	            throw new InvalidInputException("First name is required.");
	        }
	        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
	            throw new InvalidInputException("Last name is required.");
	        }
//	        if (employee.getDateOfBirth() == null) {
//	            throw new InvalidInputException("Date of birth is required.");
//	        }
//	        if (employee.getDateOfBirth().isAfter(LocalDate.now())) {
//	            throw new InvalidInputException("Date of birth cannot be in the future.");
//	        }
//	        if (!isValidEmail(employee.getEmail())) {
//	            throw new InvalidInputException("Invalid email format.");
//	        }
//	        if (!isValidPhoneNumber(employee.getPhoneNumber())) {
//	            throw new InvalidInputException("Invalid phone number format.");
//        }
//	        if (!isValidPosition(employee.getPosition())) {
//	            throw new InvalidInputException("Invalid Position.");
//	        }
	        // Add more validation rules as needed, e.g.,
	        // email format validation, phone number format validation, etc.
	    }

	    /**
	     * Validates a date to ensure it is not in the future.
	     *
	     * @param date The date to validate
	     * @param fieldName The name of the field being validated (for the error
	     * message).
	     * @throws InvalidInputException if the date is in the future.
	     */
	    public void validateDateNotFuture(LocalDate date, String fieldName) throws InvalidInputException {
	    	 if (date == null) {
	             throw new InvalidInputException(fieldName + " is required.");
	         }
	        if (date != null && date.isAfter(LocalDate.now())) {
	            throw new InvalidInputException(fieldName + " cannot be in the future.");
	        }
	    }

	    /**
	     * Validates email format.
	     *
	     * @param email The email address to validate.
	     * @return true if the email is valid, false otherwise.
	     */
	    public void isValidEmail(String email) throws InvalidInputException {
	        if (email == null) {
	        	throw new InvalidInputException("Invalid email format.");
	            //return false;
	        }
	        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
	        Pattern pattern = Pattern.compile(emailRegex);
	        Matcher matcher = pattern.matcher(email);
	        if (!matcher.matches()) {
	            throw new InvalidInputException("Invalid email format.");
	        }
	        //return matcher.matches();
	    }

	    /**
	     * Validates phone number format.
	     *
	     * @param phoneNumber The phone number to validate.
	     * @return true if the phone number is valid, false otherwise.
	     */
	    public void isValidPhoneNumber(long phoneNumber) throws InvalidInputException {
	        //String phoneNumberStr = String.valueOf(phoneNumber);
	        // Basic check: 10 digits and non-negative
	        //return phoneNumberStr.length() == 10 && phoneNumber >= 0;
	        String phoneNumberStr = String.valueOf(phoneNumber);
	        if (!Pattern.matches("\\d{10}", phoneNumberStr)) {
	            throw new InvalidInputException("Invalid phone number format. It should be 10 digits.");
	        }
	    }

	    /**
	     * Validates position
	     *
	     * @param position The position  to validate.
	     * @return true if the position is valid, false otherwise.
	     */
	    public void isValidPosition(String position) throws InvalidInputException{
	        if (position == null || position.trim().isEmpty()) {
	        	 throw new InvalidInputException("Position is required.");
	        }
	        //List of allowed positions
	        String[] validPositions = {"Manager", "Developer", "Tester", "Analyst", "Designer","Clerk","Accountant"};
	        for (String validPosition : validPositions) {
	            if (validPosition.equalsIgnoreCase(position)) {
	            	 return;
	            }
	        }
	        throw new InvalidInputException("Invalid Position.");
	    }

	    /**
	     * Validates salary.
	     *
	     * @param salary The salary to validate. 
	     * @throws InvalidInputException If the salary is invalid.
	     */
	    public void validateSalary(BigDecimal salary) throws InvalidInputException {
	        if (salary == null) {
	            throw new InvalidInputException("Salary cannot be null.");
	        }
	        if (salary.compareTo(BigDecimal.ZERO) < 0) {
	            throw new InvalidInputException("Salary cannot be negative.");
	        }
	        //  Add more rules like maximum salary limit if required
	    }
	    
	    public void validatePayrollData(Payroll payroll) throws InvalidInputException {
//	        if (payroll == null) {
//	            throw new InvalidInputException("Payroll object cannot be null.");
//	        }
//	        validatePayrollId(Payroll.getPayrollID());
//	        //validateEmployeeId(Payroll.getPayrollsForEmployee());
//	        validatePayPeriod(Payroll.getPayPeriodStartDate(), Payroll.getPayPeriodEndDate());
//	        validateAmount("Basic Salary", Payroll.getBasicSalary());
//	        validateAmount("Overtime Pay", Payroll.getOvertimePay());
//	        validateAmount("Deductions", Payroll.getDeductions());
//	        validateAmount("Net Salary", Payroll.getNetSalary());
	    	
	    	   if (payroll == null) {
	    	        throw new InvalidInputException("Payroll object cannot be null.");
	    	    }
	    	    validatePayrollId(payroll.getPayrollID());
	    	    validateEmployeeId(payroll.getEmployeeID()); // If you want to validate the employee ID too
	    	    validatePayPeriod(payroll.getPayPeriodStartDate(), payroll.getPayPeriodEndDate());
	    	    validateAmount("Basic Salary", payroll.getBasicSalary());
	    	    validateAmount("Overtime Pay", payroll.getOvertimePay());
	    	    validateAmount("Deductions", payroll.getDeductions());
	    	    validateAmount("Net Salary", payroll.getNetSalary());
	    }
	    
	    public void validatePayrollId(Integer payrollId) throws InvalidInputException {
	        if (payrollId == null) {
	            throw new InvalidInputException("Payroll ID cannot be null.");
	        }
	        if (payrollId <= 0) {
	            throw new InvalidInputException("Payroll ID must be a positive integer.");
	        }
	    }
	    
	    public void validateEmployeeId(Integer employeeId) throws InvalidInputException {
	        if (employeeId == null) {
	            throw new InvalidInputException("Employee ID cannot be null.");
	        }
	        if (employeeId <= 0) {
	            throw new InvalidInputException("Employee ID must be a positive integer.");
	        }
	    }
	    
	    private void validatePayPeriod(LocalDate startDate, LocalDate endDate) throws InvalidInputException {
	        if (startDate == null) {
	            throw new InvalidInputException("Pay period start date cannot be null.");
	        }
	        if (endDate == null) {
	            throw new InvalidInputException("Pay period end date cannot be null.");
	        }
	        if (startDate.isAfter(endDate)) {
	            throw new InvalidInputException("Pay period start date cannot be after the end date.");
	        }
	    }
	    
	    public void validateAmount(String fieldName, BigDecimal amount) throws InvalidInputException {
	        if (amount == null) {
	            throw new InvalidInputException(fieldName + " cannot be null.");
	        }
	        if (amount.compareTo(BigDecimal.ZERO) < 0) {
	            throw new InvalidInputException(fieldName + " cannot be negative.");
	        }
	    }
	    
	    public void validateTaxData(Tax tax) throws InvalidInputException {
	        if (tax == null) {
	            throw new InvalidInputException("Tax object cannot be null.");
	        }
	        validateTaxId(tax.getTaxID());
	        validateEmployeeId(tax.getEmployeeID());
	        validateTaxYear(tax.getTaxYear());
	        validateAmount("Taxable Income", tax.getTaxableIncome());
	        validateAmount("Tax Amount", tax.getTaxAmount());
	        // You might want to add a validation to check if taxAmount is non-negative,
	        // although validateAmount already does this.
	    }
	    
	    private void validateTaxId(Integer taxId) throws InvalidInputException {
	        if (taxId != null && taxId <= 0) {
	            throw new InvalidInputException("Tax ID must be a positive integer.");
	        }
	        // Allowing null assuming it might be auto-generated
	    }
	    
	    private void validateTaxYear(Integer taxYear) throws InvalidInputException {
	        if (taxYear == null) {
	            throw new InvalidInputException("Tax year cannot be null.");
	        }
	        int currentYear = Year.now().getValue();
	        if (taxYear <= 0 || taxYear > currentYear) {
	            throw new InvalidInputException("Tax year must be a valid year (1 to " + currentYear + ").");
	        }
	    }
	    
	    public void validateFinancialRecordData(Financialrecord record) throws InvalidInputException {
	        if (record == null) {
	            throw new InvalidInputException("Financial record object cannot be null.");
	        }
	        validateRecordId(record.getRecordID());
	        //validateEmployeeId(record.getEmployeeId());
	        validateRecordDate(record.getRecordDate());
	        validateDescription(record.getDescription());
	        validateAmount("Amount", record.getAmount());
	        validateRecordType(record.getRecordType());
	    }
	    
	    public void validateRecordId(Integer recordId) throws InvalidInputException {
	        if (recordId != null && recordId <= 0) {
	            throw new InvalidInputException("Record ID must be a positive integer.");
	        }
	        // Allowing null assuming it might be auto-generated
	    }
	    
	    private void validateRecordDate(LocalDate recordDate) throws InvalidInputException {
	        if (recordDate == null) {
	            throw new InvalidInputException("Record date cannot be null.");
	        }
	        if (recordDate.isAfter(LocalDate.now())) {
	            throw new InvalidInputException("Record date cannot be in the future.");
	        }
	    }
	    
	    public void validateDescription(String description) throws InvalidInputException {
	        if (description == null || description.trim().isEmpty()) {
	            throw new InvalidInputException("Description cannot be null or empty.");
	        }
	    }
	    
	    public void validateRecordType(String recordType) throws InvalidInputException {
	        if (recordType == null || recordType.trim().isEmpty()) {
	            throw new InvalidInputException("Record type cannot be null or empty.");
	        }
	        List<String> allowedRecordTypes = Arrays.asList("Income", "Expense"); // Define allowed types
	        if (!allowedRecordTypes.contains(recordType.trim())) {
	            throw new InvalidInputException("Invalid record type. Allowed types are: " + allowedRecordTypes);
	        }
	    }
	    
	    
	    
	    
	    
	    
	}




