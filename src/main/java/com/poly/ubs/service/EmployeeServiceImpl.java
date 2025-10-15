package com.poly.ubs.service;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String, EmployeeRepository> {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    protected EmployeeRepository getRepository() {
        return employeeRepository;
    }
}
