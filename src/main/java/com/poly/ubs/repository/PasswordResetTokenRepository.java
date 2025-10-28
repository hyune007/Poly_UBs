package com.poly.ubs.repository;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho thực thể PasswordResetToken
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Tìm token theo chuỗi token
     *
     * @param token chuỗi token
     * @return Optional chứa PasswordResetToken nếu tìm thấy
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * Xóa tất cả token của một khách hàng
     *
     * @param customer khách hàng
     */
    void deleteByCustomer(Customer customer);
}