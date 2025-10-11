package com.poly.ubs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="NhanVien")
public class Employee {
    @Id
    private String nv_id;
    private String nv_name;
    private String nv_password;
    private String nv_phone;
    private String nv_mail;
    private String nv_address;
    private Boolean nv_role;
    private Date nv_birth;
    @OneToMany(mappedBy = "employee")
    private List<Employee> employees;
}
