package com.poly.ubs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

/**
 * Bộ điều khiển thanh toán
 */
@Controller
public class PaymentController {
    /**
     * Hiển thị trang thanh toán
     * @param request yêu cầu HTTP
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template thanh toán
     */
    @GetMapping("/order/payment")
    public String payment(HttpServletRequest request, Model model) {
        model.addAttribute("currentURI", request.getRequestURI());
        return "container/orders/payment";
    }
}
