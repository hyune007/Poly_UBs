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
 * Lớp thực thể đại diện cho danh mục sản phẩm (Loại sản phẩm).
 * Ánh xạ tới bảng "loaisanpham" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loaisanpham")
public class Category {
    /**
     * Mã định danh duy nhất của danh mục.
     */
    @Id
    @Column(name = "lsp_id", length = 20)
    private String id;

    /**
     * Tên danh mục sản phẩm.
     */
    @Column(name = "lsp_name", length = 100)
    private String name;
}