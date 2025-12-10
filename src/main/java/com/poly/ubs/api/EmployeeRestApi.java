package com.poly.ubs.api;

import com.poly.ubs.dto.EmployeePageResponse;
import com.poly.ubs.entity.Employee;
import com.poly.ubs.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // cho phép Vue gọi
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestApi {

    @Autowired
    EmployeeServiceImpl service;

    @GetMapping
    public Page<Employee> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, 10);
        return service.findByKeyword(keyword, pageable);
    }


    // Lấy 1 nhân viên
    @GetMapping("/{id}")
    public Employee getOne(@PathVariable String id) {
        return service.findById(id);
    }

    // Thêm nhân viên
    @PostMapping
    public Employee create(@RequestBody Employee emp) {
        return service.save(emp);
    }

    // Cập nhật nhân viên
    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee emp) {
        emp.setId(id);
        return service.save(emp);
    }

    // Xóa nhân viên
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }
}
