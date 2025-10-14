package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Thực thể token để reset mật khẩu
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    /**
     * ID của token
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Token ngẫu nhiên
     */
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    /**
     * Khách hàng liên kết với token
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kh_id", nullable = false)
    private Customer customer;

    /**
     * Thời gian hết hạn của token
     */
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Kiểm tra token đã hết hạn chưa
     * @return true nếu token đã hết hạn
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}

