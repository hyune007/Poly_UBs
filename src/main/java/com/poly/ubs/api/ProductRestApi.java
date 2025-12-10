package com.poly.ubs.api;

import com.poly.ubs.entity.Category;
import com.poly.ubs.entity.Product;
import com.poly.ubs.service.CategoryServiceImpl;
import com.poly.ubs.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")  // cho phép Vue gọi
@RestController
@RequestMapping("/api/products")
public class ProductRestApi {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryService.findAll(); // trả List<Category>
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable("id") String id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return productService.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") String id, @RequestBody Product p) {
        p.setId(id);                // GÁN ID vào object
        return productService.update(p);  // GỌI update(T entity)
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        productService.deleteById(id);
    }
}
