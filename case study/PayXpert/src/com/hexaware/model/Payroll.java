package com.hexaware.model;

import java.time.LocalDate;
import java.math.BigDecimal;


//public class Payroll {
//	
//	
//	private static int PayrollID;
//	private static int EmployeeID;
//	private static LocalDate PayPeriodStartDate;
//	private static LocalDate PayPeriodEndDate;
//	private static BigDecimal BasicSalary; 
//	private static BigDecimal OvertimePay; 
//	private static BigDecimal Deductions; 
//	private static BigDecimal NetSalary;
//	
//	
//	
//	
//	public static int getPayrollID() {
//		return PayrollID;
//	}
//
//
//	public void setPayrollID(int payrollID) {
//		PayrollID = payrollID;
//	}
//
//
//	public static int getEmployeeID() {
//		return EmployeeID;
//	}
//
//
//	public void setEmployeeID(int employeeID) {
//		EmployeeID = employeeID;
//	}
//
//
//	public static LocalDate getPayPeriodStartDate() {
//		return PayPeriodStartDate;
//	}
//
//
//	public void setPayPeriodStartDate(LocalDate payPeriodStartDate) {
//		PayPeriodStartDate = payPeriodStartDate;
//	}
//
//
//	public static LocalDate getPayPeriodEndDate() {
//		return PayPeriodEndDate;
//	}
//
//
//	public void setPayPeriodEndDate(LocalDate payPeriodEndDate) {
//		PayPeriodEndDate = payPeriodEndDate;
//	}
//
//
//	public static BigDecimal getBasicSalary() {
//		return BasicSalary;
//	}
//
//
//	public void setBasicSalary(BigDecimal basicSalary) {
//		BasicSalary = basicSalary;
//	}
//
//
//	public static BigDecimal getOvertimePay() {
//		return OvertimePay;
//	}
//
//
//	public void setOvertimePay(BigDecimal overtimePay) {
//		OvertimePay = overtimePay;
//	}
//
//
//	public static BigDecimal getDeductions() {
//		return Deductions;
//	}
//
//
//	public void setDeductions(BigDecimal deductions) {
//		Deductions = deductions;
//	}
//
//
//	public static BigDecimal getNetSalary() {
//		return NetSalary;
//	}
//
//
//	public void setNetSalary(BigDecimal netSalary) {
//		NetSalary = netSalary;
//	}
//
//
//	public Payroll(int payrollID, int employeeID, LocalDate payPeriodStartDate, LocalDate payPeriodEndDate,
//			BigDecimal basicSalary, BigDecimal overtimePay, BigDecimal deductions, BigDecimal netSalary) {
//		super();
//		Payroll.PayrollID = payrollID;
//		Payroll.EmployeeID = employeeID;
//		Payroll.PayPeriodStartDate = payPeriodStartDate;
//		Payroll.PayPeriodEndDate = payPeriodEndDate;
//		Payroll.BasicSalary = basicSalary;
//		Payroll.OvertimePay = overtimePay;
//		Payroll.Deductions = deductions;
//		Payroll.NetSalary = netSalary;
//	}
//	
//	
//
//
//	public Payroll() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//
////	public Payroll(int int1, Date date, Date date2) {
////		// TODO Auto-generated constructor stub
////		super();
////	}
//	
//	public static Payroll generateNetSalary() {
//        Payroll.NetSalary = BasicSalary.add(OvertimePay).subtract(Deductions);
//		return null;
//    } 
//
//
//	@Override
//	public String toString() {
//		return "Payroll [PayrollID=" + PayrollID + ", EmployeeID=" + EmployeeID + ", PayPeriodStartDate="
//				+ PayPeriodStartDate + ", PayPeriodEndDate=" + PayPeriodEndDate + ", BasicSalary=" + BasicSalary
//				+ ", OvertimePay=" + OvertimePay + ", Deductions=" + Deductions + ", NetSalary=" + NetSalary + "]";
//	} 
//}

public class Payroll {

    private int payrollID;
    private int employeeID;
    private LocalDate payPeriodStartDate;
    private LocalDate payPeriodEndDate;
    private BigDecimal basicSalary;
    private BigDecimal overtimePay;
    private BigDecimal deductions;
    private BigDecimal netSalary;

    public Payroll() {
        // Default constructor
    }

    public Payroll(int payrollID, int employeeID, LocalDate payPeriodStartDate, LocalDate payPeriodEndDate,
                   BigDecimal basicSalary, BigDecimal overtimePay, BigDecimal deductions, BigDecimal netSalary) {
        this.payrollID = payrollID;
        this.employeeID = employeeID;
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payPeriodEndDate;
        this.basicSalary = basicSalary;
        this.overtimePay = overtimePay;
        this.deductions = deductions;
        this.netSalary = netSalary;
    }

    // Getters and Setters
    public int getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(int payrollID) {
        this.payrollID = payrollID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getPayPeriodStartDate() {
        return payPeriodStartDate;
    }

    public void setPayPeriodStartDate(LocalDate payPeriodStartDate) {
        this.payPeriodStartDate = payPeriodStartDate;
    }

    public LocalDate getPayPeriodEndDate() {
        return payPeriodEndDate;
    }

    public void setPayPeriodEndDate(LocalDate payPeriodEndDate) {
        this.payPeriodEndDate = payPeriodEndDate;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(BigDecimal overtimePay) {
        this.overtimePay = overtimePay;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(BigDecimal deductions) {
        this.deductions = deductions;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public void calculateNetSalary() {
        if (basicSalary != null && overtimePay != null && deductions != null) {
            this.netSalary = basicSalary.add(overtimePay).subtract(deductions);
        } else {
            this.netSalary = BigDecimal.ZERO;
        }
    }

    @Override
    public String toString() {
        return "Payroll [PayrollID=" + payrollID +
                ", EmployeeID=" + employeeID +
                ", PayPeriodStartDate=" + payPeriodStartDate +
                ", PayPeriodEndDate=" + payPeriodEndDate +
                ", BasicSalary=" + basicSalary +
                ", OvertimePay=" + overtimePay +
                ", Deductions=" + deductions +
                ", NetSalary=" + netSalary + "]";
    }
}

