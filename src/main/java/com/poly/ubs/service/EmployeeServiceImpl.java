package com.poly.ubs.service;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Cài đặt dịch vụ cho thực thể Employee sử dụng dịch vụ chung
 */
@Service
public class EmployeeServiceImpl extends GenericServiceImpl<Employee, String, EmployeeRepository> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    protected EmployeeRepository getRepository() {
        return employeeRepository;
    }

    /**
     * Tìm nhân viên theo email và mật khẩu
     *
     * @param email    email của nhân viên
     * @param password mật khẩu của nhân viên
     * @return nhân viên nếu tìm thấy, null nếu không tìm thấy
     */
    public Employee findByEmailAndPassword(String email, String password) {
        return employeeRepository.findByEmailAndPassword (email, password);
    }
    /**
     * Tìm nhân viên theo từ khóa với phân trang
     *
     * @param keyword từ khóa tìm kiếm (tìm theo tên)
     * @param page    số trang (bắt đầu từ 0)
     * @param size    số lượng bản ghi mỗi trang
     * @return Page chứa danh sách nhân viên
     */
    public Page<Employee> findByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of (page, size);
        if (keyword == null || keyword.trim ().isEmpty ()) {
            return employeeRepository.findAll (pageable);
        }
        return employeeRepository.findByNameContainingIgnoreCase (keyword, pageable);
    }
}
