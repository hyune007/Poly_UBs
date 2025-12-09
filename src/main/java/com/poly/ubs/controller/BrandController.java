package com.poly.ubs.controller;

import com.poly.ubs.entity.Brand;
import com.poly.ubs.service.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Quản lý thương hiệu sản phẩm.
 */
@Controller
@RequestMapping("/admin")
public class BrandController {

    @Autowired
    private BrandServiceImpl brandService;

    /**
     * Hiển thị danh sách thương hiệu.
     *
     * @param model Đối tượng Model.
     * @return Tên view quản lý thương hiệu.
     */
    @GetMapping("/brand")
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("brand", new Brand());
        return "admin/product/brand";
    }

    /**
     * Lưu thông tin thương hiệu mới hoặc cập nhật thương hiệu hiện có.
     *
     * @param brand Đối tượng Brand từ form.
     * @return Đường dẫn chuyển hướng về danh sách thương hiệu.
     */
    @PostMapping("/save")
    public String save(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        return "redirect:/admin/brand";
    }

    /**
     * Hiển thị form chỉnh sửa thương hiệu.
     *
     * @param id    ID thương hiệu cần sửa.
     * @param model Đối tượng Model.
     * @return Tên view quản lý thương hiệu với thông tin cần sửa.
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        model.addAttribute("brands", brandService.findAll());
        return "admin/product/brand";
    }

    /**
     * Xóa thương hiệu theo ID.
     *
     * @param id ID thương hiệu cần xóa.
     * @return Đường dẫn chuyển hướng về danh sách thương hiệu.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        brandService.deleteById(id);
        return "redirect:/admin/brand";
    }
}
