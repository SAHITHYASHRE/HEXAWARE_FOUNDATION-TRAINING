package com.hexaware.model;

import java.time.LocalDate;

public class Employee {
	
	private int EmployeeID;
	private String FirstName;
	private String LastName;
	private LocalDate DateOfBirth; 
	private String Gender; 
	private String Email;
	private Long PhoneNumber; 
	private String Address; 
	private String Position; 
	private LocalDate JoiningDate; 
	private LocalDate TerminationDate;
	
	public int getEmployeeID(int id) {
		return EmployeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.EmployeeID = employeeID;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public LocalDate getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Long getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public LocalDate getJoiningDate() {
		return JoiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		JoiningDate = joiningDate;
	}
	public LocalDate getTerminationDate() {
		return TerminationDate;
	}
	public void setTerminationDate(LocalDate terminationDate) {
		TerminationDate = terminationDate;
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int employeeID, String firstName, String lastName, LocalDate dateOfBirth, String gender,
			String email, Long phoneNumber, String address, String position, LocalDate joiningDate,
			LocalDate terminationDate) {
		super();
		this.EmployeeID = employeeID;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.DateOfBirth = dateOfBirth;
		this.Gender = gender;
		this.Email = email;
		this.PhoneNumber = phoneNumber;
		this.Address = address;
		this.Position = position;
		this.JoiningDate = joiningDate;
		this.TerminationDate = terminationDate;
	}
	@Override
	public String toString() {
		return "Employee [EmployeeID=" + EmployeeID + ", FirstName=" + FirstName + ", LastName=" + LastName
				+ ", DateOfBirth=" + DateOfBirth + ", Gender=" + Gender + ", Email=" + Email + ", PhoneNumber="
				+ PhoneNumber + ", Address=" + Address + ", Position=" + Position + ", JoiningDate=" + JoiningDate
				+ ", TerminationDate=" + TerminationDate + "]";
	}
	
	
	public int employeeById() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
