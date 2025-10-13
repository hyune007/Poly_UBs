package com.poly.ubs.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class ViewController {

    @RequestMapping("header")
    public String header(Model model) {
        return "/main-frame/header";
    }
    @GetMapping("home")
    public String home(Model model) {
        return "/container/home";
    }
}

