package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Bộ điều khiển xác thực người dùng
 */
@Controller
public class AuthController {
    
    @Autowired
    private CustomerRepository customerRepository;

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
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        // Kiểm tra thông tin đăng nhập
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        
        if (customer != null) {
            // Đăng nhập thành công, lưu thông tin người dùng vào session
            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", customer);
            return "redirect:/home";
        } else {
            // Đăng nhập thất bại, quay lại trang đăng nhập với thông báo lỗi
            return "redirect:/login?error=true";
        }
    }

    /**
     * Xử lý yêu cầu đăng xuất
     * @param req yêu cầu HTTP
     * @return chuyển hướng đến trang chủ
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
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