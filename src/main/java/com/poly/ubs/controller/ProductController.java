package com.poly.ubs.controller;

import com.poly.ubs.entity.Brand;
import com.poly.ubs.entity.Category;
import com.poly.ubs.entity.Product;
import com.poly.ubs.service.BrandServiceImpl;
import com.poly.ubs.service.CategoryService;
import com.poly.ubs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandServiceImpl brandServiceimpl; // mới: cần có service/ repo cho Brand

    // Danh sách (paging + filter)
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            Model model) {

        var pageable = PageRequest.of(page, size);
        var productPage = (category != null && !category.isEmpty())
                ? productService.findByCategory(category, pageable)
                : productService.findAll(pageable);

        long totalProducts = (category != null && !category.isEmpty())
                ? productService.countByCategory(category)
                : productService.count();

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categoryService.findAll());

        return "admin/products/list";
    }

    // Hiển thị form thêm
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandServiceimpl.findAll()); // <-- trả về List<Brand>
        return "admin/products/form";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Product product = productService.findById(Long.valueOf(id));
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("brands", brandServiceimpl.findAll());
        return "admin/products/form";
    }

    // Lưu (tạo hoặc cập nhật)
    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        // Nếu form gửi brand.id và category.id (nested binding), product.getBrand() và getCategory()
        // có thể chứa chỉ id — ta cần load entity thực từ DB và set lại để JPA hiểu quan hệ.
        if (product.getBrand() != null && product.getBrand().getId() != null) {
            Brand b = brandServiceimpl.findById(product.getBrand().getId());
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

    // Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.deleteById(Long.valueOf(id));
        return "redirect:/admin/products";
    }
}
