package com.poly.ubs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }
    @PostMapping("/login-post")
    public String loginPost(HttpServletRequest req){
        String email = req.getParameter ("email");
        String password = req.getParameter ("password");
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String register(){
        return "auth/register";
    }
    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "auth/forgot-password";
    }
    @GetMapping("/reset-password")
    public String resetPassword(){
        return "auth/reset-password";
    }
}