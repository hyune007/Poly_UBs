package com.poly.ubs.service;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String, EmployeeRepository> {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    protected EmployeeRepository getRepository() {
        return employeeRepository;
    }

    public Page<Employee> findByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return getRepository().findAll(pageable);
        }
        return getRepository().findByNameContainingIgnoreCase(keyword, pageable);
    }
}
