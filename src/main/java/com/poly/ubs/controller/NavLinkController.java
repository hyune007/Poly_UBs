package com.poly.ubs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavLinkController {
    @GetMapping("/home/nav-link/news")
    public String news(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-news";
    }

    @GetMapping("home/nav-link/introduction")
    public String intro(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-introduction";
    }

    @GetMapping("/home/nav-link/contact")
    public String contact(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-contact";
    }

    @GetMapping("/home/nav-link/product")
    public String product(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-product";
    }
}
