package com.poly.ubs.controller;

import com.poly.ubs.dto.OrderInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Cung cấp cấu hình Firebase cho phía frontend thông qua API.
 * Config được tải từ biến môi trường.
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController extends OrderInfoDTO {

    @Value("${FIREBASE_WEB_API_KEY:}")
    private String firebaseApiKey;

    @Value("${FIREBASE_PROJECT_ID:}")
    private String firebaseProjectId;

    /**
     * Lấy thông tin cấu hình Firebase.
     *
     * @return Map chứa các thông tin cấu hình Firebase.
     */
    @GetMapping("/firebase")
    public Map<String, String> getFirebaseConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("apiKey", firebaseApiKey);
        config.put("authDomain", firebaseProjectId + ".firebaseapp.com");
        config.put("projectId", firebaseProjectId);
        config.put("storageBucket", firebaseProjectId + ".firebasestorage.app");
        return config;
    }
}

