package com.poly.ubs.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Quản lý các chức năng quản trị viên.
 */
@RequestMapping("/admin")
@Controller
public class AdminController {

    /**
     * Hiển thị trang tổng quan quản trị.
     *
     * @param model   Đối tượng Model để truyền dữ liệu sang view.
     * @param session Phiên làm việc hiện tại.
     * @return Tên view của trang dashboard.
     */
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        Object user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("adminUser", user);
        return "admin/dashboard";
    }
}
