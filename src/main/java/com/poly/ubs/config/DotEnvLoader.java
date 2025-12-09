package com.poly.ubs.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Cấu hình tải biến môi trường từ tập tin .env.
 * <p>
 * Lớp này triển khai EnvironmentPostProcessor để can thiệp vào quá trình khởi tạo
 * Spring Boot, nạp các biến môi trường từ tập tin .env trước khi application.properties
 * được xử lý.
 * <p>
 * Mục đích:
 * - Lưu trữ thông tin nhạy cảm (API keys, mật khẩu cơ sở dữ liệu) bên ngoài mã nguồn.
 * - Hỗ trợ loại bỏ tập tin .env khỏi hệ thống quản lý phiên bản (git).
 * - Quản lý cấu hình linh hoạt cho các môi trường khác nhau.
 */
public class DotEnvLoader implements EnvironmentPostProcessor {

    /**
     * Phương thức được gọi trong quá trình khởi động ứng dụng Spring Boot,
     * trước khi ApplicationContext được khởi tạo.
     *
     * @param environment Môi trường cấu hình hiện tại của Spring Boot.
     * @param application Ứng dụng Spring đang được khởi chạy.
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Cấu hình và tải nội dung từ tập tin .env tại thư mục gốc
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        // Chuyển đổi dữ liệu từ Dotenv sang cấu trúc Map
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(e -> {
            dotenvMap.put(e.getKey(), e.getValue());
        });

        // Tích hợp biến môi trường từ .env vào Spring Environment với độ ưu tiên cao nhất
        environment.getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", dotenvMap));
    }
}