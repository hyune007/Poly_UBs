//package com.poly.ubs.controller;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
///**
// * Bộ điều khiển đơn hàng
// */
//@Controller
//public class OrderController {
//
//    /**
//     * Hiển thị trang giỏ hàng
//     *
//     * @param request yêu cầu HTTP
//     * @param model   đối tượng model để truyền dữ liệu đến view
//     * @return đường dẫn đến template giỏ hàng
//     */
//    @GetMapping("/order/shopping-cart")
//    public String shoppingCart(HttpServletRequest request, Model model) {
//        model.addAttribute ("currentURI", request.getRequestURI ());
//        return "container/orders/shopping-cart";
//    }
//
//    /**
//     * Hiển thị trang thông tin đơn hàng
//     *
//     * @param request yêu cầu HTTP
//     * @param model   đối tượng model để truyền dữ liệu đến view
//     * @return đường dẫn đến template thông tin đơn hàng
//     */
//    @GetMapping("/order/infor-order")
//    public String inforOrder(HttpServletRequest request, Model model) {
//        model.addAttribute ("currentURI", request.getRequestURI ());
//        return "container/orders/infor-order";
//    }
//
//    /**
//     * Hiển thị trang thanh toán
//     *
//     * @param request yêu cầu HTTP
//     * @param model   đối tượng model để truyền dữ liệu đến view
//     * @return đường dẫn đến template thanh toán
//     */
//    @GetMapping("/order/payment")
//    public String payment(HttpServletRequest request, Model model) {
//        model.addAttribute ("currentURI", request.getRequestURI ());
//        return "container/orders/payment";
//    }
//
//    /**
//     * Hiển thị trang hoàn thành đơn hàng
//     *
//     * @param request yêu cầu HTTP
//     * @param model   đối tượng model để truyền dữ liệu đến view
//     * @return đường dẫn đến template hoàn thành
//     */
//    @GetMapping("/order/complete")
//    public String complete(HttpServletRequest request, Model model) {
//        model.addAttribute ("currentURI", request.getRequestURI ());
//        return "container/orders/complete";
//    }
//}
