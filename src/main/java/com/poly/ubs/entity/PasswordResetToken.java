package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Lớp thực thể đại diện cho mã thông báo (token) dùng để đặt lại mật khẩu.
 * Ánh xạ tới bảng "password_reset_tokens" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    /**
     * Mã định danh duy nhất của bản ghi token.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Chuỗi token ngẫu nhiên dùng để xác thực.
     */
    @Column(name = "token", nullable = false, unique = true)
    private String token;

    /**
     * Khách hàng yêu cầu đặt lại mật khẩu.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kh_id", nullable = false)
    private Customer customer;

    /**
     * Thời gian hết hạn của token.
     */
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    /**
     * Kiểm tra xem token đã hết hạn hay chưa.
     *
     * @return True nếu thời gian hiện tại đã vượt quá thời gian hết hạn, ngược lại trả về False.
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}

