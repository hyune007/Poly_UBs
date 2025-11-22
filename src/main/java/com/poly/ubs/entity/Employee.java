package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Thực thể nhân viên
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nhanvien")
public class Employee {
    /**
     * ID của nhân viên
     */
    @Id
    @Column(name = "nv_id", length = 8)
    private String id;

    /**
     * Tên nhân viên
     */
    @Column(name = "nv_name", length = 100)
    private String name;

    /**
     * Mật khẩu của nhân viên
     */
    @Column(name = "nv_password", length = 40)
    private String password;

    /**
     * Số điện thoại của nhân viên
     */
    @Column(name = "nv_phone", length = 15)
    private String phone;

    /**
     * Email của nhân viên
     */
    @Column(name = "nv_mail", length = 50)
    private String email;

    /**
     * Địa chỉ của nhân viên
     */
    @Column(name = "nv_address", length = 100)
    private String address;

    /**
     * Vai trò của nhân viên
     */
    @Column(name = "nv_role")
    private String role;

    /**
     * Ngày sinh của nhân viên
     */
    @Column(name = "nv_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birth;
}