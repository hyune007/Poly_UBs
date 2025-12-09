package com.poly.ubs.controller;

import com.poly.ubs.entity.Customer;
import com.poly.ubs.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Quản lý thông tin khách hàng.
 */
@Controller
@RequestMapping("/admin/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    /**
     * Hiển thị danh sách khách hàng.
     *
     * @param model Đối tượng Model.
     * @return Tên view danh sách khách hàng.
     */
    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("customer", new Customer());
        return "admin/customer/customer";
    }

    /**
     * Lưu thông tin khách hàng.
     *
     * @param customer Đối tượng khách hàng.
     * @return Chuyển hướng về danh sách khách hàng.
     */
    @PostMapping("/save")
    public String save(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/admin/customer";
    }

    /**
     * Hiển thị form chỉnh sửa khách hàng.
     *
     * @param id    ID khách hàng.
     * @param model Đối tượng Model.
     * @return Tên view quản lý khách hàng.
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        return "admin/customer/customer";
    }

    /**
     * Xóa khách hàng theo ID.
     *
     * @param id ID khách hàng.
     * @return Chuyển hướng về danh sách khách hàng.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        customerService.deleteById(id);
        return "redirect:/admin/customer";
    }
}
