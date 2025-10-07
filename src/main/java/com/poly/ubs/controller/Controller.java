package com.poly.ubs.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("header")
    public String header(Model model) {
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

    @RequestMapping("order/shopping-cart")
    public String shopping(Model model) {
        return "container/orders/shopping-cart";
    }

    @RequestMapping("order/infor-order")
    public String infor(Model model) {
        return "/container/orders/infor-order";
    }

    @RequestMapping("order/payment")
    public String payment(Model model) {
        return "container/orders/payment";
    }

    @RequestMapping("order/complete")
    public String complete(Model model) {
        return "container/orders/complete";
    }

    @RequestMapping("/reset")
    public String resetPass(Model model) {
        return "/auth/reset-password";
    }
//----------------



}

