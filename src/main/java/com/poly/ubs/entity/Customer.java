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
    @Column(name = "kh_id")
    private String id;
    
    @Column(name = "kh_name")
    private String name;
    
    @Column(name = "kh_password")
    private String password;
    
    @Column(name = "kh_phone", length = 15, nullable = false)
    private String phone;
    
    @Column(name = "kh_mail", length = 50, nullable = false)
    private String email;
}