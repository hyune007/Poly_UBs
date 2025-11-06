package com.poly.ubs.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        Object user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("adminUser", user);
        return "admin/dashboard";
    }
}
