package com.poly.ubs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller để cung cấp Firebase configuration cho frontend
 * Config này sẽ được load từ biến môi trường
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${FIREBASE_WEB_API_KEY:}")
    private String firebaseApiKey;

    @Value("${FIREBASE_PROJECT_ID:}")
    private String firebaseProjectId;

    /**
     * Endpoint để lấy Firebase Web Config
     *
     * @return Map chứa Firebase configuration
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

