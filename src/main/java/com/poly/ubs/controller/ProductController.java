//package com.poly.ubs.controller;
//
//import com.poly.ubs.entity.Product;
//import com.poly.ubs.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Controller
//@RequestMapping("/admin/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService service;
//
//    // 📁 Thư mục lưu ảnh upload (nằm trong static)
//    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
//
//    // Danh sách sản phẩm theo category
//    @GetMapping
//    public String list(@RequestParam(name = "category", required = false) String category, Model model) {
//        if (category != null && !category.isEmpty()) {
//            model.addAttribute("products", service.findByCategory(category));
//            model.addAttribute("currentCategory", category);
//        } else {
//            model.addAttribute("products", service.findAll());
//            model.addAttribute("currentCategory", "");
//        }
//        return "admin/list";
//    }
//
//    // Tạo mới sản phẩm, lấy category từ URL
//    @GetMapping("/create")
//    public String createForm(@RequestParam(name = "category", required = false) String category, Model model) {
//        Product product = new Product();
//        if (category != null) {
//            product.setCategory(category);
//        }
//        model.addAttribute("product", product);
//        return "admin/form";
//    }
//
//    // ✅ Lưu sản phẩm (có upload ảnh)
//    @PostMapping("/save")
//    public String save(@ModelAttribute Product product,
//                       @RequestParam(value = "file", required = false) MultipartFile file) {
//
//        try {
//            // Nếu người dùng có upload file
//            if (file != null && !file.isEmpty()) {
//                // Tạo thư mục nếu chưa có
//                File uploadDir = new File(UPLOAD_DIR);
//                if (!uploadDir.exists()) {
//                    uploadDir.mkdirs();
//                }
//
//                // Tạo đường dẫn lưu file
//                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//                Path filePath = Paths.get(UPLOAD_DIR, fileName);
//
//                // Lưu file vào thư mục static/uploads
//                Files.write(filePath, file.getBytes());
//
//                // Gán đường dẫn truy cập ảnh (URL tĩnh)
//                String imageUrl = "/uploads/" + fileName; //sua theo phan loai
//                product.setImageUrl(imageUrl);
//            }
//
//            service.save(product);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "redirect:/admin/products?category=" + product.getCategory();
//    }
//
//    // Sửa sản phẩm
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable Long id, Model model) {
//        Product product = service.findById(id).orElse(new Product());
//        model.addAttribute("product", product);
//        return "admin/form";
//    }
//
//    // Xóa sản phẩm
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Long id,
//                         @RequestParam(name = "category", required = false) String category) {
//        service.deleteById(id);
//        if (category != null) {
//            return "redirect:/admin/products?category=" + category;
//        }
//        return "redirect:/admin/products";
//    }
//}
