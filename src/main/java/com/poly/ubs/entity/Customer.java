package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KhachHang")
public class Customer {
    @Id
    @Column(name = "kh_id", length = 8, nullable = false)
    private String id;
    
    @Column(name = "kh_name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "kh_password", length = 40, nullable = false)
    private String password;
    
    @Column(name = "kh_phone", length = 15, nullable = false)
    private String phone;
    
    @Column(name = "kh_mail", length = 50, nullable = false)
    private String email;
}