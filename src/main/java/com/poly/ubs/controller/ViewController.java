package com.poly.ubs.controller;

import com.poly.ubs.entity.Category;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.CategoryRepository;
import com.poly.ubs.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Bộ điều khiển xem giao diện
 */
@org.springframework.stereotype.Controller
public class ViewController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
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
    public String home(Model model, HttpSession session, @RequestParam("p") Optional<Integer> p, @RequestParam(value = "categoryId", required = false) String categoryId) {
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
        Pageable pageable = PageRequest.of(p.orElse(0), 18);
        Page<Product> items;

        if (categoryId != null && !categoryId.isEmpty()) {
            items = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            items = productRepository.findAll(pageable);
        }

        /**
         * Truy xuất các phần tử trong danh sách sản phẩm
         * Cái nào có mã loại sản phẩm gì thì gán đường dẫn image tương đương của loại sản phẩm đó
         */
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
        model.addAttribute("selectedCategoryId", categoryId);
        return "/container/home";
    }

    @GetMapping("/product/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        Product item = productRepository.findById(id).orElse(null);
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
        model.addAttribute("item", item);
        if (item != null && item.getCategory() != null) {
            model.addAttribute("selectedCategoryId", item.getCategory().getId());
        }
        return "/container/products/product-detail";
    }
}