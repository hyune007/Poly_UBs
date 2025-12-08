package com.poly.ubs.repository;

import com.poly.ubs.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Customer (Khách hàng).
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    /**
     * Tìm khách hàng dựa trên địa chỉ email và mật khẩu.
     *
     * @param email    Địa chỉ email.
     * @param password Mật khẩu.
     * @return Đối tượng Customer nếu tìm thấy, ngược lại trả về null.
     */
    Customer findByEmailAndPassword(String email, String password);

    /**
     * Tìm khách hàng dựa trên địa chỉ email.
     *
     * @param email Địa chỉ email.
     * @return Đối tượng Customer nếu tìm thấy, ngược lại trả về null.
     */
    Customer findByEmail(String email);
}