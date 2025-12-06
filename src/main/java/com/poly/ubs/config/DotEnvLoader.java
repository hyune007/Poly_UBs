package com.poly.ubs.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * DotEnvLoader - Class tải biến môi trường từ file .env
 *
 * Class này implement EnvironmentPostProcessor để can thiệp vào quá trình khởi tạo
 * Spring Boot và load các biến môi trường từ file .env trước khi application.properties
 * được xử lý.
 *
 * Mục đích:
 * - Cho phép lưu trữ các thông tin nhạy cảm (API keys, database passwords, etc.)
 *   trong file .env thay vì hard-code trong application.properties
 * - File .env có thể được thêm vào .gitignore để tránh commit lên repository
 * - Hỗ trợ quản lý cấu hình khác nhau cho từng môi trường (dev, staging, prod)
 */
public class DotEnvLoader implements EnvironmentPostProcessor {

    /**
     * Phương thức được Spring Boot gọi tự động trong quá trình khởi động ứng dụng,
     * trước khi ApplicationContext được tạo.
     *
     * @param environment - Môi trường cấu hình của Spring Boot, chứa tất cả các property sources
     * @param application - Instance của SpringApplication đang được khởi động
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Bước 1: Cấu hình và load file .env
        Dotenv dotenv = Dotenv.configure()
                .directory("./")           // Tìm file .env trong thư mục gốc của project
                .ignoreIfMissing()         // Không throw exception nếu file .env không tồn tại
                .load();                   // Thực hiện load các biến môi trường

        // Bước 2: Chuyển đổi các entry từ Dotenv sang Map<String, Object>
        // Map này sẽ được sử dụng để tạo PropertySource cho Spring
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(e -> {
            // Duyệt qua từng cặp key-value trong file .env và thêm vào Map
            dotenvMap.put(e.getKey(), e.getValue());
        });

        // Bước 3: Thêm các biến môi trường từ .env vào Spring Environment
        // addFirst() đảm bảo rằng các giá trị từ .env có độ ưu tiên cao nhất
        // Nếu có cùng một key trong application.properties, giá trị từ .env sẽ được sử dụng
        environment.getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", dotenvMap));
    }
}