package com.poly.ubs.repository;

import com.poly.ubs.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Employee (Nhân viên).
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    /**
     * Tìm kiếm nhân viên theo tên (không phân biệt hoa thường) có hỗ trợ phân trang.
     *
     * @param keyword  Từ khóa tìm kiếm tên nhân viên.
     * @param pageable Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách nhân viên.
     */
    Page<Employee> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    /**
     * Tìm nhân viên dựa trên email và mật khẩu.
     *
     * @param email    Địa chỉ email.
     * @param password Mật khẩu.
     * @return Đối tượng Employee nếu tìm thấy, ngược lại trả về null.
     */
    Employee findByEmailAndPassword(String email, String password);

    /**
     * Tìm nhân viên dựa trên địa chỉ email.
     *
     * @param email Địa chỉ email.
     * @return Đối tượng Employee nếu tìm thấy, ngược lại trả về null.
     */
    Employee findByEmail(String email);
}
