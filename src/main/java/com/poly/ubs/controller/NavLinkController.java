package com.poly.ubs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Điều hướng các trang tin tức, giới thiệu, liên hệ.
 */
@Controller
@RequestMapping("/home/nav-link")
public class NavLinkController {
    /**
     * Hiển thị trang tin tức.
     */
    @GetMapping("/news")
    public String news(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-news";
    }

    /**
     * Hiển thị trang giới thiệu.
     */
    @GetMapping("/introduction")
    public String intro(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-introduction";
    }

    /**
     * Hiển thị trang liên hệ.
     */
    @GetMapping("/contact")
    public String contact(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-contact";
    }

    /**
     * Hiển thị trang sản phẩm.
     */
    @GetMapping("/product")
    public String product(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-product";
    }
}
