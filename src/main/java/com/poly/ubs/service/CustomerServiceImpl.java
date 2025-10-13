package com.poly.ubs.service;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Cài đặt dịch vụ cho thực thể Customer sử dụng dịch vụ chung
 */
public class CustomerServiceImpl extends GenericServiceImpl<Customer, String, CustomerRepository>{

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    protected CustomerRepository getRepository() {
        return customerRepository;
    }
}
