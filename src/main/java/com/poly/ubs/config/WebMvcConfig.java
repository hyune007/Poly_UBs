package com.poly.ubs.config;

import com.poly.ubs.interceptor.AdminAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Cấu hình Web MVC cho ứng dụng.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AdminAuthInterceptor adminAuthInterceptor;

    /**
     * Đăng ký các bộ chặn (interceptor) cho ứng dụng.
     * Cấu hình chặn các yêu cầu tới đường dẫn /admin/** bằng AdminAuthInterceptor.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthInterceptor)
                .addPathPatterns("/admin/**");
    }
}

