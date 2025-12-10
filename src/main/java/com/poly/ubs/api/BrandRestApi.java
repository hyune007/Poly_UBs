package com.poly.ubs.api;

import com.poly.ubs.entity.Brand;
import com.poly.ubs.service.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // cho phép Vue gọi
@RestController
@RequestMapping("/api/brands")
public class BrandRestApi {

    @Autowired
    BrandServiceImpl brandService;

    // GET ALL
    @GetMapping
    public List<Brand> findAll() {
        return brandService.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Brand findById(@PathVariable("id") String id) {
        return brandService.findById(id);
    }

    // CREATE
    @PostMapping
    public Brand create(@RequestBody Brand brand) {
        return brandService.save(brand);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Brand update(@PathVariable("id") String id, @RequestBody Brand brand) {
        brand.setId(id);
        return brandService.update(brand);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        brandService.deleteById(id);
    }
}
