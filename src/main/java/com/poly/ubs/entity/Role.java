package com.poly.ubs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "role")
public class Role {
    @Id
    @Column(name = "role_id")
    private String id;
    @Column(name = "role_name")
    private String name;
}
