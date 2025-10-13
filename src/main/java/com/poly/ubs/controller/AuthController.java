package com.poly.ubs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Bộ điều khiển xác thực người dùng
 */
@Controller
public class AuthController {
    /**
     * Hiển thị trang đăng nhập
     * @return tên template đăng nhập
     */
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }
    
    /**
     * Xử lý yêu cầu đăng nhập
     * @param req yêu cầu HTTP
     * @return chuyển hướng đến trang chủ
     */
    @PostMapping("/login-post")
    public String loginPost(HttpServletRequest req){
        String email = req.getParameter ("email");
        String password = req.getParameter ("password");
        return "redirect:/home";
    }

    /**
     * Hiển thị trang đăng ký
     * @return tên template đăng ký
     */
    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }
    
    /**
     * Hiển thị trang quên mật khẩu
     * @return tên template quên mật khẩu
     */
    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "auth/forgot-password";
    }
    
    /**
     * Hiển thị trang đặt lại mật khẩu
     * @return tên template đặt lại mật khẩu
     */
    @GetMapping("/reset-password")
    public String resetPassword(){
        return "auth/reset-password";
    }
}