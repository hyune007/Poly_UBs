package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Bộ điều khiển xem giao diện
 */
@org.springframework.stereotype.Controller
public class ViewController {
    @Autowired
    private ProductRepository productRepository;
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
     * @param session đối tượng session để lấy thông tin người dùng
     * @return đường dẫn đến template trang chủ
     */
    @GetMapping("home")
    public String home(Model model, HttpSession session, @RequestParam("p") Optional<Integer> p) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        Pageable pageable = PageRequest.of(p.orElse(0), 18);
        Page<Product> items = productRepository.findAll(pageable);
        for (Product item : items) {
            String folder = "";
            switch (item.getCategory().getId()) {
                case "LSP01": folder = "phone/"; break;
                case "LSP02": folder = "laptop/"; break;
                case "LSP03": folder = "pad/"; break;
                case "LSP04": folder = "smartwatch/"; break;
                case "LSP05": folder = "headphone/"; break;
                case "LSP06": folder = "keyboard/"; break;
                case "LSP07": folder = "mouse/"; break;
                case "LSP08": folder = "screen/"; break;
                case "LSP09": folder = "speaker/"; break;
                default: folder = "other/";
            }
            item.setImage("products/" + folder + item.getImage());
        }
        model.addAttribute("items", items);
        return "/container/home";
    }

}