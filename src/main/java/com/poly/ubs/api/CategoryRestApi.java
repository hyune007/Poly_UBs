package com.poly.ubs.api;

import com.poly.ubs.entity.Category;
import com.poly.ubs.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API cho Category (Danh mục sản phẩm)
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")  // cho phép Vue gọi
public class CategoryRestApi {

    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * Lấy tất cả danh mục
     * GET /api/categories
     */
    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    /**
     * Lấy danh mục theo ID
     * GET /api/categories/{id}
     */
    @GetMapping("/{id}")
    public Category getById(@PathVariable String id) {
        return categoryService.findById(id);
    }
}
