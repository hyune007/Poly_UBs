package com.poly.ubs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/order/shopping-cart")
    public String shoppingCart(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "container/orders/shopping-cart";
    }

    @GetMapping("/order/infor-order")
    public String inforOrder(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "container/orders/infor-order";
    }
    @GetMapping("/order/complete")
    public String complete(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "container/orders/complete";
    }
}
