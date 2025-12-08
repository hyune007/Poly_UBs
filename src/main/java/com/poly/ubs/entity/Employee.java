package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Lớp thực thể đại diện cho nhân viên.
 * Ánh xạ tới bảng "nhanvien" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nhanvien")
public class Employee {
    /**
     * Mã định danh duy nhất của nhân viên.
     */
    @Id
    @Column(name = "nv_id", length = 8)
    private String id;

    /**
     * Họ và tên đầy đủ của nhân viên.
     */
    @Column(name = "nv_name", length = 100)
    private String name;

    /**
     * Mật khẩu đăng nhập hệ thống.
     */
    @Column(name = "nv_password", length = 40)
    private String password;

    /**
     * Số điện thoại liên lạc.
     */
    @Column(name = "nv_phone", length = 15)
    private String phone;

    /**
     * Địa chỉ email liên lạc.
     */
    @Column(name = "nv_mail", length = 50)
    private String email;

    /**
     * Địa chỉ thường trú.
     */
    @Column(name = "nv_address", length = 100)
    private String address;

    /**
     * Vai trò/chức vụ của nhân viên trong hệ thống.
     */
    @Column(name = "nv_role")
    private String role;

    /**
     * Ngày sinh của nhân viên.
     */
    @Column(name = "nv_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birth;
}