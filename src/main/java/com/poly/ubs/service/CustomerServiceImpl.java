package com.poly.ubs.service;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cài đặt dịch vụ cho thực thể Customer sử dụng dịch vụ chung
 */
@Service
public class CustomerServiceImpl extends GenericServiceImpl<Customer, String, CustomerRepository> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    protected CustomerRepository getRepository() {
        return customerRepository;
    }

    /**
     * Tìm khách hàng theo email và mật khẩu
     *
     * @param email    email của khách hàng
     * @param password mật khẩu của khách hàng
     * @return khách hàng nếu tìm thấy, null nếu không tìm thấy
     */
    public Customer findByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword (email, password);
    }

    /**
     * Tìm khách hàng theo email
     *
     * @param email email của khách hàng
     * @return khách hàng nếu tìm thấy, null nếu không tìm thấy
     */
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail (email);
    }
}