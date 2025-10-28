package com.poly.ubs.repository;

import com.poly.ubs.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Customer
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    /**
     * Tìm khách hàng theo email và mật khẩu
     *
     * @param email    email của khách hàng
     * @param password mật khẩu của khách hàng
     * @return Customer nếu tìm thấy, null nếu không
     */
    Customer findByEmailAndPassword(String email, String password);

    /**
     * Tìm khách hàng theo email
     *
     * @param email email của khách hàng
     * @return Customer nếu tìm thấy, null nếu không
     */
    Customer findByEmail(String email);
}