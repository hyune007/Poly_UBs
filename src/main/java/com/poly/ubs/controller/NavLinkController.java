package com.poly.ubs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home/nav-link")
public class NavLinkController {
    @GetMapping("/news")
    public String news(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-news";
    }

    @GetMapping("/introduction")
    public String intro(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-introduction";
    }

    @GetMapping("/contact")
    public String contact(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-contact";
    }

    @GetMapping("/product")
    public String product(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "/nav-link/nav-link-product";
    }
}
