CREATE DATABASE Poly_UBs;
USE Poly_UBs;
create table member (
  mem_id VARCHAR(8) NOT NULL,
  mem_name VARCHAR(100) NOT NULL,
  mem_password VARCHAR(40) NOT NULL,
  mem_phone VARCHAR(15) NOT NULL,
  mem_mail VARCHAR(50) NOT NULL,
  mem_register_date TIMESTAMP NOT NULL,
  PRIMARY KEY (mem_id)
);

create table employee (
  emp_id VARCHAR(8) NOT NULL,
  emp_name VARCHAR(100) NOT NULL,
  emp_password VARCHAR(40) NOT NULL,
  emp_phone VARCHAR(15) NOT NULL,
  emp_mail VARCHAR(50) NOT NULL,
  emp_register_date TIMESTAMP NOT NULL,
  PRIMARY KEY (emp_id)
);

create table admin (
  admin_id VARCHAR(8) NOT NULL,
  admin_name VARCHAR(100) NOT NULL,
  admin_password VARCHAR(40) NOT NULL,
  admin_phone VARCHAR(15) NOT NULL,
  admin_mail VARCHAR(50) NOT NULL,
  admin_register_date TIMESTAMP NOT NULL,
  PRIMARY KEY (admin_id)
);

drop table member
drop table employee
drop table admin
