package com.hexaware.main;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.hexaware.exception.DatabaseConnectionException;
import com.hexaware.exception.EmployeeNotFoundException;
import com.hexaware.exception.FinancialRecordException;
import com.hexaware.exception.PayrollGenerationException;
import com.hexaware.exception.TaxCalculationException;
import com.hexaware.interfaceimp.EmployeeServiceimp;
import com.hexaware.interfaceimp.FinancialRecordServiceimp;
import com.hexaware.interfaceimp.PayrollServiceimp;
import com.hexaware.interfaceimp.TaxServiceimp;
import com.hexaware.model.Employee;
import com.hexaware.model.Payroll;
import com.hexaware.model.Tax;
import com.hexaware.model.Financialrecord;

public class PayrollManagementSystem {
	
	public static void main(String[] args) throws SQLException, DatabaseConnectionException, EmployeeNotFoundException, PayrollGenerationException, TaxCalculationException, FinancialRecordException {
		
		Scanner sc = new Scanner(System.in);
		EmployeeServiceimp es = new EmployeeServiceimp();
		PayrollServiceimp ps = new PayrollServiceimp(null);
		TaxServiceimp ts = new TaxServiceimp(null);
		FinancialRecordServiceimp frs = new FinancialRecordServiceimp(null);
		es.ShowConnection();
		
		while(true) {
			
			   System.out.println("\n========= Payroll Management Menu =========");
	            System.out.println("1. Add Employee");
	            System.out.println("2. View All Employees");
	            System.out.println("3. View Employee By ID");
	            System.out.println("4. Update Employee");
	            System.out.println("5. Delete Employee");
	            System.out.println("6. Generate Payroll");
	            System.out.println("7. Add Payroll");
	            System.out.println("8. View Payroll by ID");
	            System.out.println("9. View Payrolls for Employee");
	            System.out.println("10. View Payrolls for Period");
	            System.out.println("12. Calculate and Add Tax"); 
	            System.out.println("13. View Tax by ID");
	            System.out.println("14. View Taxes for Employee");
	            System.out.println("15. View Taxes for Year");
	            System.out.println("16. Add Financial Record"); 
	            System.out.println("17. View Financial Record by ID");
	            System.out.println("18. View Financial Records for Employee");
	            System.out.println("19. View Financial Records for Date");
	            System.out.println("20. View Financial Records by Type");
	            System.out.println("21. View Total Income for Employee");
	            System.out.println("22. View Total Expenses for Employee");
	            System.out.println("23. Exit");
	            System.out.print("Enter your choice: ");
	            
	            int choice = sc.nextInt();
	            sc.nextLine();
	            
	            try {
	            
	            switch(choice) {
	            
	            
	            case 1 -> {
                    Employee emp = readEmployeeDetails(sc);
                    es.addEmployee(emp);
                    System.out.println("Employee added successfully.");
                    break;
                }
                case 2 -> {
                    List<Employee> employees = es.viewEmployee();
                    employees.forEach(System.out::println);
                    break;
                }
                case 3 -> {
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    Employee emp = es.employeeById(id);
                    System.out.println(emp != null ? emp : "Employee not found.");
                    break;
                }
                case 4 -> {
                    System.out.print("Enter ID of employee to update: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    Employee updatedEmp = readEmployeeDetails(sc);
                    updatedEmp.setEmployeeID(id);
                    es.updateEmployee(updatedEmp);
                    System.out.println("Employee updated.");
                    break;
                }
                case 5 -> {
                    System.out.print("Enter Employee ID to delete: ");
                    int id = sc.nextInt();
                    es.deleteEmployee(id);
                    System.out.println("Employee removed.");
                    break;
                }
                case 6 ->{
                	 List<Payroll> payroll = ps.generatePayroll();
                     payroll.forEach(System.out::println);
                     break;
                }
                case 7 -> {
                    Payroll payroll = readPayrollDetails(sc);
                    ps.insertPayroll(payroll);
                    System.out.println("Payroll added successfully.");
                    break;
                }
                case 8 -> {
                    System.out.print("Enter Payroll ID: ");
                    int id = sc.nextInt();
                    Payroll payroll = ps.getPayrollById(id);
                    System.out.println(payroll != null ? payroll : "Payroll not found.");
                    break;
                }
                case 9 -> {
                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    List<Payroll> list = ps.getPayrollsForEmployee(empId);
                    list.forEach(System.out::println);
                    break;
                }
                case 10 -> {
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String start = sc.next();
                    System.out.print("Enter End Date (YYYY-MM-DD): ");
                    String end = sc.next();
                    List<Payroll> list = ps.getPayrollsForPeriod(start, end);
                    list.forEach(System.out::println);
                    break;
                }
                case 12 -> { // Calculate and Add Tax
                    System.out.print("Enter Employee ID to calculate tax for: ");
                    int empId = sc.nextInt();
                    System.out.print("Enter Tax Year: ");
                    int taxYear = sc.nextInt();
                    sc.nextLine(); // Consume the newline

                    BigDecimal taxAmount = ts.CalculateTax(empId, taxYear);
                    BigDecimal taxableIncome = ts.getTaxableIncome(empId, taxYear); // Get taxable income

                    if (taxableIncome != null && taxAmount != null) {
                        Tax tax = new Tax(0, empId, taxYear, taxableIncome, taxAmount); // taxId is auto-generated
                        ts.insertTax(tax);
                        System.out.println("Tax calculated and added successfully. Tax Amount: " + taxAmount);
                    } else {
                        System.out.println("Could not calculate tax.  Check Employee ID and Tax Year, and ensure payroll data exists.");
                    }
                    break;
                }
                case 13 -> {
                    System.out.print("Enter Tax ID to view: ");
                    int taxId = sc.nextInt();
                    Tax tax = ts.getTaxById(taxId);
                    System.out.println(tax != null ? tax : "Tax record not found.");
                    break;
                }
                case 14 -> {
                    System.out.print("Enter Employee ID to view taxes for: ");
                    int empId = sc.nextInt();
                    List<Tax> taxes = ts.getTaxForEmployee(empId);
                    if (taxes != null && !taxes.isEmpty()) {
                        taxes.forEach(System.out::println);
                    } else {
                        System.out.println("No tax records found for this employee.");
                    }
                    break;
                }
                case 15 -> {
                    System.out.print("Enter Tax Year to view taxes for: ");
                    int taxYear = sc.nextInt();
                    List<Tax> taxes = ts.getTaxesForYear(taxYear);
                    if (taxes != null && !taxes.isEmpty()) {
                        taxes.forEach(System.out::println);
                    } else {
                        System.out.println("No tax records found for this year.");
                    }
                    break;
                }
                case 16 -> { // Add Financial Record
                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.print("Enter Record Date (YYYY-MM-DD): ");
                    LocalDate recordDate = LocalDate.parse(sc.nextLine());
                    System.out.print("Enter Description: ");
                    String description = sc.nextLine();
                    System.out.print("Enter Amount: ");
                    BigDecimal amount = new BigDecimal(sc.nextLine());
                    System.out.print("Enter Record Type (Income, Expense): ");
                    String recordType = sc.nextLine();

                    frs.addFinancialRecord(empId, recordDate, description, amount, recordType);
                    System.out.println("Financial record added successfully.");
                    break;
                }
                case 17 -> { // View Financial Record by ID
                    System.out.print("Enter Financial Record ID: ");
                    int recordId = sc.nextInt();
                    Financialrecord record = frs.getFinancialRecordById(recordId);
                    System.out.println(record != null ? record : "Financial record not found.");
                    break;
                }
                case 18 -> { // View Financial Records for Employee
                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    List<Financialrecord> records = frs.getFinancialRecordsForEmployee(empId);
                    if (records != null && !records.isEmpty()) {
                        records.forEach(System.out::println);
                    } else {
                        System.out.println("No financial records found for this employee.");
                    }
                    break;
                }
                case 19 -> { // View Financial Records for Date
                    System.out.print("Enter Record Date (YYYY-MM-DD): ");
                    LocalDate recordDate = LocalDate.parse(sc.next());
                    List<Financialrecord> records = frs.getFinancialRecordsForDate(recordDate);
                    if (records != null && !records.isEmpty()) {
                        records.forEach(System.out::println);
                    } else {
                        System.out.println("No financial records found for this date.");
                    }
                    break;
                }
                case 20 -> { //View Financial Records by Type
                    System.out.print("Enter Record Type (Income, Expense): ");
                    String recordType = sc.nextLine();
                    List<Financialrecord> records = frs.getFinancialRecordsForType(recordType);
                    if(records!=null && !records.isEmpty()){
                        records.forEach(System.out::println);
                    } else{
                        System.out.println("No financial records found for this type");
                    }
                    break;
                }
                case 21 -> {
                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    BigDecimal totalIncome = frs.getTotalIncomeForEmployee(empId);
                    System.out.println("Total Income for Employee " + empId + ": " + totalIncome);
                    break;
                }
                case 22 -> {
                    System.out.print("Enter Employee ID: ");
                    int empId = sc.nextInt();
                    BigDecimal totalExpenses = frs.getTotalExpensesForEmployee(empId);
                    System.out.println("Total Expenses for Employee " + empId + ": " + totalExpenses);
                    break;
                }
                case 23 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice. Try again.");
	            }
	            }catch (SQLException e) {
	                System.out.println("Error: " + e.getMessage());
	            }
	            
	            
	            
	            }
		}
	 private static Employee readEmployeeDetails(Scanner scanner) {
	        System.out.print("First Name: ");
	        String firstName = scanner.nextLine();
	        System.out.print("Last Name: ");
	        String lastName = scanner.nextLine();
	        System.out.print("Date of Birth (YYYY-MM-DD): ");
	        LocalDate dob = LocalDate.parse(scanner.nextLine());
	        System.out.print("Gender: ");
	        String gender = scanner.nextLine();
	        System.out.print("Email: ");
	        String email = scanner.nextLine();
	        System.out.print("Phone Number: ");
	        long phone = Long.parseLong(scanner.nextLine());
	        System.out.print("Address: ");
	        String address = scanner.nextLine();
	        System.out.print("Position: ");
	        String position = scanner.nextLine();
	        System.out.print("Joining Date (YYYY-MM-DD): ");
	        LocalDate joinDate = LocalDate.parse(scanner.nextLine());
	        System.out.print("Termination Date (YYYY-MM-DD or press Enter if not applicable): ");
	        String termination = scanner.nextLine();
	        LocalDate terminationDate = termination.isEmpty() ? null : LocalDate.parse(termination);

	        // Temporary ID (must be unique and valid)
	        System.out.print("Enter Employee ID: ");
	        int id = Integer.parseInt(scanner.nextLine());

	        return new Employee(id, firstName, lastName, dob, gender, email, phone, address, position, joinDate, terminationDate);
	    }
	 
	 private static Payroll readPayrollDetails(Scanner sc) {
//		    System.out.print("Payroll ID: ");
//		    int payrollId = sc.nextInt();
//		    System.out.print("Employee ID: ");
//		    int empId = sc.nextInt();
//		    sc.nextLine(); // consume newline
//		    System.out.print("Pay Period Start Date (YYYY-MM-DD): ");
//		    LocalDate start = LocalDate.parse(sc.nextLine());
//		    System.out.print("Pay Period End Date (YYYY-MM-DD): ");
//		    LocalDate end = LocalDate.parse(sc.nextLine());
//		    System.out.print("Basic Salary: ");
//		    BigDecimal basic = new BigDecimal(sc.nextLine());
//		    System.out.print("Overtime Pay: ");
//		    BigDecimal overtime = new BigDecimal(sc.nextLine());
//		    System.out.print("Deductions: ");
//		    BigDecimal deductions = new BigDecimal(sc.nextLine());
//
//		    Payroll payroll = new Payroll(payrollId, empId, start, end, basic, overtime, deductions, BigDecimal.ZERO);
//		    Payroll.getNetSalary();
//		    return payroll;
		 
		 System.out.print("Payroll ID: ");
		    int payrollId = sc.nextInt();
		    System.out.print("Employee ID: ");
		    int empId = sc.nextInt();
		    sc.nextLine(); // consume newline
		    System.out.print("Pay Period Start Date (YYYY-MM-DD): ");
		    LocalDate start = LocalDate.parse(sc.nextLine());
		    System.out.print("Pay Period End Date (YYYY-MM-DD): ");
		    LocalDate end = LocalDate.parse(sc.nextLine());
		    System.out.print("Basic Salary: ");
		    BigDecimal basic = new BigDecimal(sc.nextLine());
		    System.out.print("Overtime Pay: ");
		    BigDecimal overtime = new BigDecimal(sc.nextLine());
		    System.out.print("Deductions: ");
		    BigDecimal deductions = new BigDecimal(sc.nextLine());

		    Payroll payroll = new Payroll(payrollId, empId, start, end, basic, overtime, deductions, BigDecimal.ZERO);
		    
		    // Call the instance method on the object to calculate net salary
		    BigDecimal netSalary = payroll.getNetSalary();
		    payroll.setNetSalary(netSalary); // Ensure net salary is updated in the object

		    return payroll;
		}

}
