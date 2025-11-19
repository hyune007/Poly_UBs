package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("customer", new Customer());
        return "admin/customer/customer";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        return "admin/customer/customer";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        customerService.deleteById(id);
        return "redirect:/customer";
    }
}
