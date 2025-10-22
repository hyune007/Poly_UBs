package com.poly.ubs.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Cấu hình Firebase cho ứng dụng
 */
@Configuration
public class FirebaseConfig {

    /**
     * Đường dẫn đến file service account của Firebase
     */
    @Value("${firebase.service-account}")
    private String serviceAccountPath;

    /**
     * Khởi tạo Firebase App với credentials từ service account
     *
     * @throws Exception nếu không tìm thấy file service account hoặc khởi tạo thất bại
     */
    @PostConstruct
    public void init() throws Exception {
        InputStream serviceAccount = getClass ().getClassLoader ().getResourceAsStream (serviceAccountPath);
        if (serviceAccount == null) {
            throw new RuntimeException ("Cannot find Firebase service account file: " + serviceAccountPath);
        }

        FirebaseOptions options = FirebaseOptions.builder ()
                .setCredentials (GoogleCredentials.fromStream (serviceAccount))
                .build ();

        if (FirebaseApp.getApps ().isEmpty ()) {
            FirebaseApp.initializeApp (options);
        }
    }
}