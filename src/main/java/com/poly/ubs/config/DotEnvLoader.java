
package com.poly.ubs.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Load biến môi trường từ file .env trước khi Spring Boot xử lý application.properties
 */
public class DotEnvLoader implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(e -> {
            dotenvMap.put(e.getKey(), e.getValue());
        });

        environment.getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", dotenvMap));
    }
}