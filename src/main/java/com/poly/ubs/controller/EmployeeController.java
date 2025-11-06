package com.poly.ubs.controller;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Bộ điều khiển quản lý nhân viên cho trang quản trị
 */
@Controller
@RequestMapping("/admin/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    /**
     * Hiển thị danh sách nhân viên với phân trang và tìm kiếm
     *
     * @param model   đối tượng model để truyền dữ liệu đến view
     * @param page    số trang (mặc định 0)
     * @param keyword từ khóa tìm kiếm (tùy chọn)
     * @return đường dẫn đến template danh sách nhân viên
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
     * Hiển thị form thêm nhân viên mới
     *
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template form
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "admin/employee/form";
    }

    /**
     * Lưu nhân viên (tạo mới hoặc cập nhật)
     *
     * @param employee đối tượng nhân viên cần lưu
     * @return chuyển hướng về danh sách nhân viên
     */
    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/admin/employee";
    }

    /**
     * Hiển thị form sửa nhân viên
     *
     * @param id    ID của nhân viên cần sửa
     * @param model đối tượng model để truyền dữ liệu đến view
     * @return đường dẫn đến template form
     * @throws RuntimeException nếu không tìm thấy nhân viên
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
     * Xóa nhân viên
     *
     * @param id ID của nhân viên cần xóa
     * @return chuyển hướng về danh sách nhân viên
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        employeeService.deleteById(id);
        return "redirect:/admin/employee";
    }
}

