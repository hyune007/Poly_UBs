package com.poly.ubs.service;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến khách hàng.
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
     * Tìm kiếm khách hàng dựa trên địa chỉ email và mật khẩu.
     *
     * @param email    Địa chỉ email.
     * @param password Mật khẩu.
     * @return Đối tượng Customer nếu tìm thấy, ngược lại trả về null.
     */
    public Customer findByEmailAndPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    /**
     * Tìm kiếm khách hàng dựa trên địa chỉ email.
     *
     * @param email Địa chỉ email.
     * @return Đối tượng Customer nếu tìm thấy, ngược lại trả về null.
     */
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}