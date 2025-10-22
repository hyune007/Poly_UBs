package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Thực thể danh mục sản phẩm
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loaisanpham")
public class Category {
    /**
     * ID của danh mục
     */
    @Id
    @Column(name = "lsp_id", length = 20)
    private String id;

    /**
     * Tên danh mục
     */
    @Column(name = "lsp_name", length = 100)
    private String name;
}