package com.poly.ubs.controller;

import com.poly.ubs.entity.Category;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.service.CategoryServiceImpl;
import com.poly.ubs.service.ProductServiceImpl;
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
 * Quản lý hiển thị các trang giao diện chính.
 */
@org.springframework.stereotype.Controller
public class ViewController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Lấy danh sách danh mục sản phẩm.
     *
     * @return Danh sách Category.
     */
    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    /**
     * Hiển thị header của trang.
     *
     * @param model Đối tượng Model.
     * @return Tên view header.
     */
    @RequestMapping("header")
    public String header(Model model) {
        return "/main-frame/header";
    }       
    /**
         * Chuyển hướng từ root đến trang chủ
         *
         * @return chuỗi chuyển hướng đến trang chủ
    */
    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/home";
    }
    /**
     * Hiển thị trang chủ với danh sách sản phẩm.
     *
     * @param model      Đối tượng Model.
     * @param session    Phiên làm việc hiện tại.
     * @param p          Trang hiện tại.
     * @param categoryId ID danh mục cần lọc.
     * @return Tên view trang chủ.
     */
    @GetMapping("home")
    public String home(Model model, HttpSession session, @RequestParam("p") Optional<Integer> p, @RequestParam(value = "categoryId", required = false) String categoryId) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        // Chỉ set loggedInUser vào model nếu là Customer
        if (loggedInUser instanceof Customer) {
            model.addAttribute("loggedInUser", loggedInUser);
        } else {
            model.addAttribute("loggedInUser", null);
        }
        Pageable pageable = PageRequest.of(p.orElse(0), 18);
        Page<Product> items;

        if (categoryId != null && !categoryId.isEmpty()) {
            items = productService.findByCategoryId(categoryId, pageable);
            Category category = categoryService.findById(categoryId);
            if (category != null) {
                model.addAttribute("selectedCategoryName", category.getName());
            }
        } else {
            items = productService.findAll(pageable);
            model.addAttribute("selectedCategoryName", "Tất cả sản phẩm");
        }

        // Cập nhật đường dẫn hình ảnh cho từng sản phẩm
        for (Product item : items) {
            String folder = "";
            switch (item.getCategory().getId()) {
                case "LSP01":
                    folder = "phone/";
                    break;
                case "LSP02":
                    folder = "laptop/";
                    break;
                case "LSP03":
                    folder = "pad/";
                    break;
                case "LSP04":
                    folder = "smartwatch/";
                    break;
                case "LSP05":
                    folder = "headphone/";
                    break;
                case "LSP06":
                    folder = "keyboard/";
                    break;
                case "LSP07":
                    folder = "mouse/";
                    break;
                case "LSP08":
                    folder = "screen/";
                    break;
                case "LSP09":
                    folder = "speaker/";
                    break;
                default:
                    folder = "other/";
            }
            item.setImage("products/" + folder + item.getImage());
        }
        model.addAttribute("items", items);
        model.addAttribute("selectedCategoryId", categoryId);
        return "/container/home";
    }

    /**
     * Hiển thị trang chi tiết sản phẩm.
     *
     * @param id    ID sản phẩm.
     * @param model Đối tượng Model.
     * @return Tên view chi tiết sản phẩm.
     */
    @GetMapping("/product/detail/{id}")
    public String detail(@PathVariable("id") String id, Model model) {
        Product item = productService.findById(id);
        String folder = "";
        switch (item.getCategory().getId()) {
            case "LSP01":
                folder = "phone/";
                break;
            case "LSP02":
                folder = "laptop/";
                break;
            case "LSP03":
                folder = "pad/";
                break;
            case "LSP04":
                folder = "smartwatch/";
                break;
            case "LSP05":
                folder = "headphone/";
                break;
            case "LSP06":
                folder = "keyboard/";
                break;
            case "LSP07":
                folder = "mouse/";
                break;
            case "LSP08":
                folder = "screen/";
                break;
            case "LSP09":
                folder = "speaker/";
                break;
            default:
                folder = "other/";
        }
        item.setImage("products/" + folder + item.getImage());
        model.addAttribute("item", item);
        if (item != null && item.getCategory() != null) {
            model.addAttribute("selectedCategoryId", item.getId());
        }
        return "/container/products/product-detail";
    }

    /**
     * Tìm kiếm và lọc sản phẩm.
     *
     * @param keyword    Từ khóa tìm kiếm.
     * @param categoryId ID danh mục.
     * @param p          Trang hiện tại.
     * @param model      Đối tượng Model.
     * @return Tên view kết quả tìm kiếm.
     */
    @GetMapping("/product/search")
    public String searchProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "p", required = false) Optional<Integer> p,
            Model model
    ) {
        Pageable pageable = PageRequest.of(p.orElse(0), 18);
        Page<Product> items;

        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasCategory = categoryId != null && !categoryId.isBlank();

        if (hasCategory && hasKeyword) {
            items = productService.findByCategoryAndName(categoryId, keyword, pageable);
        } else if (hasCategory) {
            items = productService.findByCategoryId(categoryId, pageable);
        } else if (hasKeyword) {
            items = productService.findByNameContaining(keyword, pageable);
        } else {
            items = productService.findAll(pageable);
        }

        String selectedCategoryName;
        if (hasCategory) {
            Category category = categoryService.findById(categoryId);
            selectedCategoryName = (category != null ? category.getName() : "Danh mục không xác định");
        } else if (hasKeyword) {
            selectedCategoryName = "Kết quả tìm kiếm cho: \"" + keyword + "\"";
        } else {
            selectedCategoryName = "Tất cả sản phẩm";
        }

        for (Product item : items) {
            String folder = "";
            switch (item.getCategory().getId()) {
                case "LSP01":
                    folder = "phone/";
                    break;
                case "LSP02":
                    folder = "laptop/";
                    break;
                case "LSP03":
                    folder = "pad/";
                    break;
                case "LSP04":
                    folder = "smartwatch/";
                    break;
                case "LSP05":
                    folder = "headphone/";
                    break;
                case "LSP06":
                    folder = "keyboard/";
                    break;
                case "LSP07":
                    folder = "mouse/";
                    break;
                case "LSP08":
                    folder = "screen/";
                    break;
                case "LSP09":
                    folder = "speaker/";
                    break;
                default:
                    folder = "other/";
            }
            item.setImage("products/" + folder + item.getImage());
        }
        model.addAttribute("items", items);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedCategoryName", selectedCategoryName); // 🟩 Thêm dòng này
        model.addAttribute("keyword", keyword);

        return "/container/home";
    }

}
