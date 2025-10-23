package com.poly.ubs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Thực thể khách hàng
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khachhang")
public class Customer {
    /**
     * ID của khách hàng
     */
    @Id
    @Column(name = "kh_id", length = 8)
    private String id;

    /**
     * Tên khách hàng
     */
    @Column(name = "kh_name", length = 100)
    private String name;

    /**
     * Mật khẩu của khách hàng
     */
    @Column(name = "kh_password", length = 40)
    private String password;

    /**
     * Số điện thoại của khách hàng
     */
    @Column(name = "kh_phone", length = 15)
    private String phone;

    /**
     * Email của khách hàng
     */
    @Column(name = "kh_mail", length = 50)
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;
}