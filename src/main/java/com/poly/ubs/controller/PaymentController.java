package com.poly.ubs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class PaymentController {
    @GetMapping("/order/payment")
    public String payment(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "container/orders/payment";
    }
}
