package com.poly.ubs.utils;

import com.poly.ubs.utils.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**") // áp dụng cho tất cả
                .excludePathPatterns(
                        "/login", "/login-post",
                        "/register", "/register-post",
                        "/forgot-password", "/reset-password",
                        "/css/**", "/js/**", "/images/**", "/photos/**"
                );
    }
}
