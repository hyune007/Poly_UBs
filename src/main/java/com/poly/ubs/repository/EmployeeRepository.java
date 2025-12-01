package com.poly.ubs.repository;

import com.poly.ubs.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    /**
     * Tìm nhân viên theo tên (không phân biệt chữ hoa/thường) với phân trang
     *
     * @param keyword  từ khóa tìm kiếm
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách nhân viên
     */
    Page<Employee> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Employee findByEmailAndPassword(String email, String password);

    /**
     * Tìm nhân viên theo email
     *
     * @param email email của nhân viên
     * @return Employee nếu tìm thấy, null nếu không
     */
    Employee findByEmail(String email);
}
