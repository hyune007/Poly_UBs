package com.poly.ubs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho vai trò người dùng (Role).
 * Ánh xạ tới bảng "role" trong cơ sở dữ liệu.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    /**
     * Mã định danh duy nhất của vai trò.
     */
    @Id
    @Column(name = "role_id")
    private String id;

    /**
     * Tên hiển thị của vai trò.
     */
    @Column(name = "role_name")
    private String name;
}
