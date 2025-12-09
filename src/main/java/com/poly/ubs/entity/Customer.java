package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp thực thể đại diện cho khách hàng.
 * Ánh xạ tới bảng "khachhang" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khachhang")
public class Customer {
    /**
     * Mã định danh duy nhất của khách hàng.
     */
    @Id
    @Column(name = "kh_id", length = 8)
    private String id;

    /**
     * Họ và tên đầy đủ của khách hàng.
     */
    @Column(name = "kh_name", length = 100)
    private String name;

    /**
     * Mật khẩu đăng nhập (đã mã hóa nếu có).
     */
    @Column(name = "kh_password", length = 40)
    private String password;

    /**
     * Số điện thoại liên lạc.
     */
    @Column(name = "kh_phone", length = 15)
    private String phone;

    /**
     * Địa chỉ email liên lạc.
     */
    @Column(name = "kh_mail", length = 50)
    private String email;

    /**
     * Vai trò phân quyền của khách hàng trong hệ thống.
     */
    @Column(name = "kh_role")
    private String role;

    /**
     * Danh sách các địa chỉ giao hàng của khách hàng.
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Address> addresses;

    /**
     * Danh sách các đơn hàng đã đặt của khách hàng.
     */
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Bill> bills = new ArrayList<>();
}