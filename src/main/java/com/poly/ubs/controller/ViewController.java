package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Bộ điều khiển xem giao diện
 */
@org.springframework.stereotype.Controller
public class ViewController {

    /**
     * Hiển thị phần header
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template header
     */
    @RequestMapping("header")
    public String header(Model model) {
        return "/main-frame/header";
    }
    
    /**
     * Hiển thị trang chủ
     * @param model đối tượng model để truyền dữ liệu đến view
     * @param session đối tượng session để lấy thông tin người dùng
     * @return đường dẫn đến template trang chủ
     */
    @GetMapping("home")
    public String home(Model model, HttpSession session) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        return "/container/home";
    }

//    @GetMapping("/profile")
//    public String userProfile(Model model, HttpSession session) {
//        return "/container/user/profile";
//    }
}