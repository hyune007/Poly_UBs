package com.poly.ubs.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Cấu hình tích hợp Firebase vào ứng dụng.
 */
@Configuration
public class FirebaseConfig {

    /**
     * Đường dẫn tới tập tin cấu hình Service Account của Firebase.
     */
    @Value("${firebase.service-account}")
    private String serviceAccountPath;

    /**
     * Khởi tạo kết nối tới Firebase sử dụng thông tin xác thực từ tập tin Service Account.
     * Phương thức này được gọi tự động sau khi bean được khởi tạo.
     *
     * @throws Exception Nếu không tìm thấy tập tin cấu hình hoặc khởi tạo thất bại.
     */
    @PostConstruct
    public void init() throws Exception {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(serviceAccountPath);
        if (serviceAccount == null) {
            throw new RuntimeException("Cannot find Firebase service account file: " + serviceAccountPath);
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}