package com.poly.ubs.repository;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể PasswordResetToken (Mã đặt lại mật khẩu).
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Tìm kiếm thông tin token dựa trên chuỗi token.
     *
     * @param token Chuỗi token cần tìm.
     * @return Optional chứa PasswordResetToken nếu tìm thấy.
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * Xóa tất cả các token liên quan đến một khách hàng cụ thể.
     *
     * @param customer Đối tượng khách hàng cần xóa token.
     */
    void deleteByCustomer(Customer customer);
}