package com.poly.ubs.controller;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword) {

        int pageSize = 10;
        Page<Employee> employeePage = employeeService.findByKeyword(keyword, page, pageSize);

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("keyword", keyword);

        return "admin/employee/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "admin/employee/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/admin/employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, Model model) {
        Employee employee = employeeService.findOptionalById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên có ID: " + id));
        model.addAttribute("employee", employee);
        return "admin/employee/form";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        employeeService.deleteById(id);
        return "redirect:/admin/employees";
    }
}
