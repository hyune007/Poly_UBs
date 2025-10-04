package com.poly.ubs.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("header")
    public String index(Model model) {
        return "/main-frame/header";
    }

    @RequestMapping("login")
    public String login(Model model) {
        return "/auth/login";
    }

    @RequestMapping("register")
    public String register(Model model) {
        return "/auth/register";
    }

    @RequestMapping("home")
    public String home(Model model) {
        return "/container/home";
    }

    @RequestMapping("cart")
    public String cart(Model model) {
        return "/container/shopping-cart";
    }

    @RequestMapping("forgotpassword")
    public String forgotpassword(Model model) {
        return "/auth/forgot-password";
    }


}

