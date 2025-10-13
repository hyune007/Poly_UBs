package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Thực thể nhân viên
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhanVien")
public class Employee {
    /**
     * ID của nhân viên
     */
    @Id
    @Column(name = "nv_id")
    private String id;
    
    /**
     * Tên nhân viên
     */
    @Column(name = "nv_name")
    private String name;
    
    /**
     * Mật khẩu của nhân viên
     */
    @Column(name = "nv_password")
    private String password;
    
    /**
     * Số điện thoại của nhân viên
     */
    @Column(name = "nv_phone")
    private String phone;
    
    /**
     * Email của nhân viên
     */
    @Column(name = "nv_mail")
    private String mail;
    
    /**
     * Địa chỉ của nhân viên
     */
    @Column(name = "nv_address")
    private String address;
    
    /**
     * Vai trò của nhân viên (true: admin, false: staff)
     */
    @Column(name = "nv_role")
    private Boolean role;
    
    /**
     * Ngày sinh của nhân viên
     */
    @Column(name = "nv_birth")
    private Date birth;
}