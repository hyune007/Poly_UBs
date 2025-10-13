package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * Xử lý yêu cầu đăng ký
     * @param req yêu cầu HTTP
     * @return chuyển hướng đến trang đăng nhập với thông báo thành công
     */
    @PostMapping("/register-post")
    public String registerPost(HttpServletRequest req, RedirectAttributes redirectAttributes) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        
        // Kiểm tra xem email đã tồn tại chưa
        if (customerRepository.findByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng!");
            return "redirect:/register";
        }
        
        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp nhau không
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp!");
            return "redirect:/register";
        }
        
        // Tạo khách hàng mới
        Customer customer = new Customer();
        customer.setId(generateCustomerId());
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setPassword(password);
        
        // Lưu khách hàng vào cơ sở dữ liệu
        customerRepository.save(customer);
        
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login";
    }
    
    /**
     * Tạo ID cho khách hàng mới
     * @return ID duy nhất cho khách hàng
     */
    private String generateCustomerId() {
        // Lấy tất cả các ID khách hàng hiện có
        String prefix = "KH";
        int maxId = 0;
        
        // Duyệt qua tất cả khách hàng để tìm số ID lớn nhất
        for (Customer customer : customerRepository.findAll()) {
            if (customer.getId().startsWith(prefix)) {
                try {
                    int idNum = Integer.parseInt(customer.getId().substring(prefix.length()));
                    if (idNum > maxId) {
                        maxId = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu không parse được số
                }
            }
        }
        
        // Tạo ID mới bằng cách tăng số ID lớn nhất lên 1
        return prefix + String.format("%03d", maxId + 1);
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