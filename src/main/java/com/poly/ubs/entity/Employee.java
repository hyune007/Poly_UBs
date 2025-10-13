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
    @Column(name = "nv_id")
    private String id;
    
    @Column(name = "nv_name")
    private String name;
    
    @Column(name = "nv_password")
    private String password;
    
    @Column(name = "nv_phone")
    private String phone;
    
    @Column(name = "nv_mail")
    private String mail;
    
    @Column(name = "nv_address")
    private String address;
    
    @Column(name = "nv_role")
    private Boolean role;
    
    @Column(name = "nv_birth")
    private Date birth;
}