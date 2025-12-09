package com.poly.ubs.controller;

import com.poly.ubs.entity.Category;
import com.poly.ubs.entity.Customer;
import com.poly.ubs.entity.Product;
import com.poly.ubs.service.BrandServiceImpl;
import com.poly.ubs.service.CategoryServiceImpl;
import com.poly.ubs.service.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * Xử lý lọc và tìm kiếm sản phẩm.
 */
@org.springframework.stereotype.Controller
public class FilterController {

    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private BrandServiceImpl brandService;

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @RequestMapping("filter/header")
    public String header(Model model) {
        return "/main-frame/header";
    }

    /**
     * Hiển thị danh sách sản phẩm theo danh mục và phân trang.
     *
     * @param model      Đối tượng Model.
     * @param session    Phiên làm việc hiện tại.
     * @param p          Trang hiện tại.
     * @param categoryId ID danh mục sản phẩm.
     * @return Tên view hiển thị sản phẩm.
     */
    @GetMapping("/home/product/list")
    public String home(Model model, HttpSession session, @RequestParam("p") Optional<Integer> p, @RequestParam(value = "categoryId", required = false) String categoryId) {
        Object loggedInUser = session.getAttribute("loggedInUser");

        // Chỉ set loggedInUser vào model nếu là Customer
        if (loggedInUser instanceof Customer) {
            model.addAttribute("loggedInUser", (Customer) loggedInUser);
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

        // Cập nhật đường dẫn hình ảnh dựa trên danh mục sản phẩm
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
        model.addAttribute("brands", brandService.getBrands());
        model.addAttribute("items", items);
        model.addAttribute("selectedCategoryId", categoryId);
        return "/nav-link/product-filter";
    }

    /**
     * Lọc sản phẩm theo giá và thương hiệu.
     *
     * @param model   Đối tượng Model.
     * @param min     Giá tối thiểu.
     * @param max     Giá tối đa.
     * @param sort    Tiêu chí sắp xếp.
     * @param brandId ID thương hiệu.
     * @param p       Trang hiện tại.
     * @return Tên view kết quả lọc.
     */
    @GetMapping("/product/filter")
    public String searchByPriceAndBrand(
            Model model,
            @RequestParam(value = "min", required = false) Double min,
            @RequestParam(value = "max", required = false) Double max,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "brandId", required = false) String brandId,
            @RequestParam(value = "p", required = false) Integer p) {

        double minPrice = (min != null) ? min : 0.0;
        double maxPrice = (max != null) ? max : 1_000_000_000;

        // Xác định sắp xếp
        Sort sortOrder = Sort.by("price");
        if ("desc".equalsIgnoreCase(sort)) {
            sortOrder = sortOrder.descending();
        } else {
            sortOrder = sortOrder.ascending(); // mặc định
        }

        Pageable pageable = PageRequest.of(p != null ? p : 0, 18, sortOrder);
        Page<Product> items;

        // Nếu người dùng chọn hãng => lọc theo hãng + giá
        if (brandId != null && !brandId.isEmpty()) {
            items = productService.findByPriceAndBrand(minPrice, maxPrice, brandId, pageable);
        } else {
            items = productService.findByPrice(minPrice, maxPrice, pageable);
        }

        // Gán đường dẫn hình ảnh tương ứng với category
        for (Product item : items) {
            String folder = "other/";
            if (item.getCategory() != null && item.getCategory().getId() != null) {
                switch (item.getCategory().getId()) {
                    case "LSP01" -> folder = "phone/";
                    case "LSP02" -> folder = "laptop/";
                    case "LSP03" -> folder = "pad/";
                    case "LSP04" -> folder = "smartwatch/";
                    case "LSP05" -> folder = "headphone/";
                    case "LSP06" -> folder = "keyboard/";
                    case "LSP07" -> folder = "mouse/";
                    case "LSP08" -> folder = "screen/";
                    case "LSP09" -> folder = "speaker/";
                }
            }
            item.setImage("products/" + folder + item.getImage());
        }

        // Lấy danh sách brand để hiển thị combobox
        model.addAttribute("brands", brandService.getBrands());

        // Truyền dữ liệu về view
        model.addAttribute("items", items);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("sort", sort);
        model.addAttribute("selectedBrandId", brandId);
        model.addAttribute("selectedCategoryName", "Tất cả sản phẩm");

        return "nav-link/product-filter";
    }


}