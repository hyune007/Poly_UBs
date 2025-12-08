package com.poly.ubs.controller;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Quản lý thông tin nhân viên trong trang quản trị.
 */
@Controller
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    /**
     * Hiển thị danh sách nhân viên với phân trang và tìm kiếm.
     *
     * @param model Đối tượng Model.
     * @param page Số trang hiện tại (mặc định là 0).
     * @param keyword Từ khóa tìm kiếm.
     * @return Tên view danh sách nhân viên.
     */
    @GetMapping
    public String list(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword) {

        int pageSize = 10;
        Page<Employee> employeePage = employeeService.findByKeyword(keyword, page, pageSize);

        model.addAttribute("employee", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("keyword", keyword);

        return "admin/employee/list";
    }

    /**
     * Hiển thị form tạo mới nhân viên.
     *
     * @param model Đối tượng Model.
     * @return Tên view form nhân viên.
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "admin/employee/form";
    }

    /**
     * Lưu thông tin nhân viên mới hoặc cập nhật nhân viên hiện có.
     *
     * @param employee Đối tượng nhân viên.
     * @return Chuyển hướng về danh sách nhân viên.
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/admin/employee";
    }

    /**
     * Hiển thị form chỉnh sửa nhân viên.
     *
     * @param id ID nhân viên cần sửa.
     * @param model Đối tượng Model.
     * @return Tên view form nhân viên.
     * @throws RuntimeException Nếu không tìm thấy nhân viên.
     */
    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable String id, Model model) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            throw new RuntimeException("Không tìm thấy nhân viên có ID: " + id);
        }
        model.addAttribute("employee", employee);
        return "admin/employee/form";
    }

    /**
     * Xóa nhân viên theo ID.
     *
     * @param id ID nhân viên cần xóa.
     * @return Chuyển hướng về danh sách nhân viên.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        employeeService.deleteById(id);
        return "redirect:/admin/employee";
    }
}

