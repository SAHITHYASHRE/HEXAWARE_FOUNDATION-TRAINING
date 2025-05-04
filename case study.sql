create table employee(
employee_id int AUTO_INCREMENT primary key,
first_name varchar(255) not null,
last_name varchar(255) not null,
dob date not null,
gender varchar(255),
email varchar(255) unique,
phone_number varchar(255),
address varchar(255),
position varchar(255),
doj date not null,
termination_date date null
);

CREATE table payroll(
payroll_id int AUTO_INCREMENT primary key,
employee_id int,
foreign key (employee_id) references employee(employee_id),
PayPeriodStartDate date not null,
PayPeriodEndDate date not null,
basic_salary decimal not null,
overtime_pay decimal(10,2) default 0.00,
deductions decimal(10,2) default 0.00,
net_salary decimal not null
);

create table tax(
tax_id int AUTO_INCREMENT primary key,
employee_id int,
foreign key (employee_id) references employee(employee_id),
tax_year int not null,
taxable_income decimal(10,2) not null,
tax_amt decimal(10,2) not null
);

create table financialRecord(
record_id int AUTO_INCREMENT primary key,
employee_id int,
foreign key (employee_id) references employee(employee_id),
record_date date not null,
description varchar(255),
amount decimal(10,2) not null,
record_type ENUM('Income', 'Expense', 'Tax Payment', 'Bonus', 'Other') NOT NULL
);

