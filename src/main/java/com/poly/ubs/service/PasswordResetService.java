package com.poly.ubs.service;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.PasswordResetToken;
import com.poly.ubs.repository.CustomerRepository;
import com.poly.ubs.repository.PasswordResetTokenRepository;
import com.poly.ubs.utils.MailSender;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service xử lý chức năng reset mật khẩu
 */
@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Tạo token reset mật khẩu và gửi email
     *
     * @param email email của khách hàng
     * @throws RuntimeException nếu email không tồn tại
     */
    @Transactional
    public void createPasswordResetToken(String email) {
        // Tìm khách hàng theo email
        Customer customer = customerRepository.findByEmail (email);
        if (customer == null) {
            throw new RuntimeException ("Email không tồn tại trong hệ thống");
        }

        // Xóa tất cả token cũ của khách hàng này
        tokenRepository.deleteByCustomer (customer);

        // Tạo token mới
        String token = UUID.randomUUID ().toString ();
        PasswordResetToken resetToken = new PasswordResetToken ();
        resetToken.setToken (token);
        resetToken.setCustomer (customer);
        resetToken.setExpiryDate (LocalDateTime.now ().plusMinutes (5)); // Hết hạn sau 5 phút

        tokenRepository.save (resetToken);

        // Tạo link reset password
        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        // Tạo nội dung email HTML
        String emailBody = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;">
                    <h2 style="color: #333; text-align: center;">🔐 Đặt lại mật khẩu</h2>
                    <p>Xin chào <strong>%s</strong>,</p>
                    <p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn tại <strong>Poly_UBs</strong>.</p>
                    <p>Vui lòng click vào nút bên dưới để đặt lại mật khẩu:</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                            Đặt lại mật khẩu
                        </a>
                    </div>
                    <p style="color: #666; font-size: 14px;">⏰ Link này sẽ hết hạn sau <strong>5 phút</strong>.</p>
                    <p style="color: #666; font-size: 14px;">Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>
                    <hr style="margin: 20px 0; border: none; border-top: 1px solid #ddd;">
                    <p style="color: #999; font-size: 12px; text-align: center;">© 2025 Poly_UBs - Tech Store</p>
                </div>
                """.formatted (customer.getName (), resetLink);

        // Gửi email
        MailSender.send (customer.getEmail (), "Yêu cầu đặt lại mật khẩu - Poly_UBs", emailBody);
    }

    /**
     * Xác thực token và đổi mật khẩu mới
     *
     * @param token       chuỗi token
     * @param newPassword mật khẩu mới
     * @throws RuntimeException nếu token không hợp lệ hoặc đã hết hạn
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // Tìm token
        PasswordResetToken resetToken = tokenRepository.findByToken (token)
                .orElseThrow (() -> new RuntimeException ("Token không hợp lệ"));

        // Kiểm tra token đã hết hạn chưa
        if (resetToken.isExpired ()) {
            tokenRepository.delete (resetToken);
            throw new RuntimeException ("Link đã hết hạn. Vui lòng yêu cầu đặt lại mật khẩu mới");
        }

        // Cập nhật mật khẩu mới
        Customer customer = resetToken.getCustomer ();
        customer.setPassword (newPassword);
        customerRepository.save (customer);

        // Xóa token sau khi đã sử dụng
        tokenRepository.delete (resetToken);
    }

    /**
     * Kiểm tra token có hợp lệ không
     *
     * @param token chuỗi token
     * @return true nếu token hợp lệ và chưa hết hạn
     */
    public boolean validateToken(String token) {
        return tokenRepository.findByToken (token)
                .map (resetToken -> !resetToken.isExpired ())
                .orElse (false);
    }
}

