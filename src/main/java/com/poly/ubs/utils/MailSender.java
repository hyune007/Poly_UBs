package com.poly.ubs.utils;

import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Tiện ích hỗ trợ gửi email thông qua giao thức SMTP.
 */
@Component("appMailSender")
public class MailSender {

    /**
     * Tên đăng nhập email (được gán từ config)
     */
    private static String USERNAME;
    /**
     * Mật khẩu ứng dụng email (được gán từ config)
     */
    private static String PASSWORD;
    @Value("${spring.mail.username}")
    private String emailUsername;
    @Value("${spring.mail.password}")
    private String emailPassword;

    /**
     * Gửi email với nội dung định dạng HTML.
     *
     * @param to      Địa chỉ email người nhận.
     * @param subject Tiêu đề của email.
     * @param body    Nội dung email (hỗ trợ mã HTML).
     */
    public static void send(String to, String subject, String body) {
        if (USERNAME == null || PASSWORD == null) {
            System.err.println("Mail configuration is missing. Cannot send email to " + to);
            return;
        }

        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(
                props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject, "UTF-8");
            message.setContent(body, "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo giá trị static từ bean instance sau khi Spring inject xong
     */
    @PostConstruct
    public void init() {
        USERNAME = this.emailUsername;
        PASSWORD = this.emailPassword;
    }
}
