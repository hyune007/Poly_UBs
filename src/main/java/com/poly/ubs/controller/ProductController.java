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
 * Bộ điều khiển quản lý sản phẩm cho trang quản trị
 */
@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Hiển thị danh sách sản phẩm với phân trang và bộ lọc
     *
     * @param page số trang (mặc định 0)
     * @param size kích thước trang (mặc định 10)
     * @param category tên danh mục để lọc (tùy chọn)
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template danh sách sản phẩm
     */
    private BrandServiceImpl brandServiceimpl;

    // Danh sách (paging + filter)
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category,
            Model model) {

        var pageable = PageRequest.of (page, size);
        var productPage = (category != null && !category.isEmpty ())
                ? productService.findByCategoryName (category, pageable)
                : productService.findAll (pageable);

        long totalProducts = (category != null && !category.isEmpty ())
                ? productService.countByCategoryName (category)
                : productService.count ();

        /**
         * Hiển thị form thêm sản phẩm mới
         * @param model đối tượng model để truyền dữ liệu đến view
         * @return đường dẫn đến template form
         */
        model.addAttribute ("currentPage", page);
        model.addAttribute ("totalPages", productPage.getTotalPages ());
        model.addAttribute ("totalProducts", totalProducts);
        model.addAttribute ("selectedCategory", category);
        model.addAttribute ("categories", categoryService.findAll ());

        return "admin/product/list";
    }

    /**
     * Hiển thị form sửa sản phẩm
     *
     * @param id    ID của sản phẩm cần sửa
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template form hoặc redirect nếu không tìm thấy
     */
    // Hiển thị form thêm
    @GetMapping("/create")
    public String createForm(Model model) {
        /**
         * Lưu sản phẩm (tạo mới hoặc cập nhật)
         * @param product đối tượng sản phẩm cần lưu
         * @return chuyển hướng về danh sách sản phẩm
         */
        model.addAttribute ("categories", categoryService.findAll ());
        model.addAttribute ("brands", brandServiceimpl.findAll ()); // <-- trả về List<Brand>
        return "admin/product/form";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Product product = productService.findById (id);
        if (product == null) {
            return "redirect:/admin/product";
        }
        model.addAttribute ("product", product);
        model.addAttribute ("categories", categoryService.findAll ());
        model.addAttribute ("brands", brandServiceimpl.findAll ());
        return "admin/product/form";
    }

    /**
     * Xóa sản phẩm
     *
     * @param id ID của sản phẩm cần xóa
     * @return chuyển hướng về danh sách sản phẩm
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Product product) {
        // Nếu form gửi brand.id và category.id (nested binding), product.getBrand() và getCategory()
        // có thể chứa chỉ id — ta cần load entity thực từ DB và set lại để JPA hiểu quan hệ.
        if (product.getBrand () != null && product.getBrand ().getId () != null) {
            Brand b = brandServiceimpl.findById (product.getBrand ().getId ());
            product.setBrand (b);
        } else {
            product.setBrand (null);
        }

        if (product.getCategory () != null && product.getCategory ().getId () != null) {
            Category c = categoryService.findById (product.getCategory ().getId ());
            product.setCategory (c);
        } else {
            product.setCategory (null);
        }

        productService.save (product);
        return "redirect:/admin/product";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.deleteById (id);
        return "redirect:/admin/product";
    }
}
