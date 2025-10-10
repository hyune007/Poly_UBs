package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhanVien")
public class Employee {
    @Id
    @Column(name = "nv_id", length = 8, nullable = false)
    private String id;
    
    @Column(name = "nv_name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "nv_password", length = 40, nullable = false)
    private String password;
    
    @Column(name = "nv_phone", length = 15, nullable = false)
    private String phone;
    
    @Column(name = "nv_mail", length = 50, nullable = false)
    private String mail;
    
    @Column(name = "nv_address", length = 100, nullable = false)
    private String address;
    
    @Column(name = "nv_role", nullable = false)
    private Boolean role;
    
    @Column(name = "nv_birth", nullable = false)
    private Date birth;
}