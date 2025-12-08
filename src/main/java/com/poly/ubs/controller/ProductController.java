package com.poly.ubs.controller;

import com.poly.ubs.entity.Brand;
import com.poly.ubs.entity.Category;
import com.poly.ubs.entity.Product;
import com.poly.ubs.service.BrandServiceImpl;
import com.poly.ubs.service.CategoryServiceImpl;
import com.poly.ubs.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Quản lý sản phẩm trong trang quản trị.
 */
@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private BrandServiceImpl brandService;

    /**
     * Hiển thị danh sách sản phẩm với phân trang và bộ lọc.
     *
     * @param page Số trang hiện tại.
     * @param size Số lượng sản phẩm trên mỗi trang.
     * @param category Danh mục cần lọc.
     * @param model Đối tượng Model.
     * @return Tên view danh sách sản phẩm.
     */
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            Model model) {

        var pageable = PageRequest.of(page, size);
        var productPage = (category != null && !category.isEmpty())
                ? productService.findByCategoryName(category, pageable)
                : productService.findAll(pageable);

        long totalProducts = (category != null && !category.isEmpty())
                ? productService.countByCategoryName(category)
                : productService.count();

        // Xử lý đường dẫn hình ảnh cho từng sản phẩm
        for (Product item : productPage.getContent()) {
            if (item.getCategory() != null) {
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
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categoryService.findAll());

        return "admin/product/list";
    }

    /**
     * Hiển thị form tạo mới sản phẩm.
     *
     * @param model Đối tượng Model.
     * @return Tên view form sản phẩm.
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "admin/product/form";
    }

    /**
     * Hiển thị form chỉnh sửa sản phẩm.
     *
     * @param id ID sản phẩm cần sửa.
     * @param model Đối tượng Model.
     * @return Tên view form sản phẩm.
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandService.findAll());
        return "admin/product/form";
    }

    /**
     * Lưu thông tin sản phẩm (xử lý cả thương hiệu và danh mục).
     *
     * @param product Đối tượng sản phẩm.
     * @return Chuyển hướng về danh sách sản phẩm.
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        // Nếu form gửi brand.id và category.id (nested binding), product.getBrand() và getCategory()
        // có thể chứa chỉ id — ta cần load entity thực từ DB và set lại để JPA hiểu quan hệ.
        if (product.getBrand() != null && product.getBrand().getId() != null) {
            Brand b = brandService.findById(product.getBrand().getId());
            product.setBrand(b);
        } else {
            product.setBrand(null);
        }

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category c = categoryService.findById(product.getCategory().getId());
            product.setCategory(c);
        } else {
            product.setCategory(null);
        }

        productService.save(product);
        return "redirect:/admin/products";
    }

    /**
     * Xóa sản phẩm theo ID.
     *
     * @param id ID sản phẩm.
     * @return Chuyển hướng về danh sách sản phẩm.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }
}