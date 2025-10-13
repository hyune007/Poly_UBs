package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Thực thể khách hàng
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhachHang")
public class Customer {
    /**
     * ID của khách hàng
     */
    @Id
    @Column(name = "kh_id")
    private String id;
    
    /**
     * Tên khách hàng
     */
    @Column(name = "kh_name")
    private String name;
    
    /**
     * Mật khẩu của khách hàng
     */
    @Column(name = "kh_password")
    private String password;
    
    /**
     * Số điện thoại của khách hàng
     */
    @Column(name = "kh_phone")
    private String phone;
    
    /**
     * Email của khách hàng
     */
    @Column(name = "kh_mail")
    private String email;
}