package com.poly.ubs.utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.Authenticator;

/**
 * Lớp tiện ích để gửi email
 */
public class MailSender {
    /**
     * Tên đăng nhập email
     */
    private static final String USERNAME = "huysclone001@gmail.com";

    /**
     * Mật khẩu ứng dụng email
     */
    private static final String PASSWORD = "zygp gdwv zpyw yuyk";

    /**
     * Gửi email với nội dung HTML
     * @param to địa chỉ email người nhận
     * @param subject tiêu đề email
     * @param body nội dung email (hỗ trợ HTML)
     */
    public static void send(String to, String subject, String body) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
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
}
