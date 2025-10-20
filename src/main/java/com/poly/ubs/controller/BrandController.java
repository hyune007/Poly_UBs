package com.poly.ubs.controller;

import com.poly.ubs.entity.Brand
import com.poly.ubs.service.BrandServiceImpl
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandServiceImpl brandService;

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        model.addAttribute("brand", new Brand());
        return "brand";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("brand") Brand brand) {
        brandService.save(brand);
        return "redirect:/brand";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id).orElse(new Brand()));
        model.addAttribute("brands", brandService.findAll());
        return "brand";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return "redirect:/brand";
    }
}
