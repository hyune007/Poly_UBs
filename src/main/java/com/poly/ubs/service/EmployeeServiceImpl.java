package com.poly.ubs.service;

import com.poly.ubs.entity.Employee;
import com.poly.ubs.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến nhân viên.
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
     * Tìm kiếm nhân viên dựa trên địa chỉ email và mật khẩu.
     *
     * @param email    Địa chỉ email.
     * @param password Mật khẩu.
     * @return Đối tượng Employee nếu tìm thấy, ngược lại trả về null.
     */
    public Employee findByEmailAndPassword(String email, String password) {
        return employeeRepository.findByEmailAndPassword(email, password);
    }

    /**
     * Tìm kiếm nhân viên theo từ khóa với phân trang.
     *
     * @param keyword Từ khóa tìm kiếm (theo tên).
     * @param page    Chỉ số trang (bắt đầu từ 0).
     * @param size    Số lượng bản ghi trên mỗi trang.
     * @return Trang kết quả chứa danh sách nhân viên.
     */
    public Page<Employee> findByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll(pageable);
        }
        return employeeRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }
}