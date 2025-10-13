package com.poly.ubs.controller;

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
     * @return đường dẫn đến template trang chủ
     */
    @GetMapping("home")
    public String home(Model model) {
        return "/container/home";
    }
}
