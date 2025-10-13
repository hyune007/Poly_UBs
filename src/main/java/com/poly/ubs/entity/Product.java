package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Thực thể sản phẩm
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "SanPham")
public class Product  {
    /**
     * ID của sản phẩm
     */
    @Id
    @Column(name = "sp_id")
    private String id;

    /**
     * Tên sản phẩm
     */
    @Column(name = "sp_name")
    private String name;

    /**
     * Giá sản phẩm
     */
    @Column(name = "sp_price")
    private int price;

    /**
     * Mô tả sản phẩm
     */
    @Column(name = "sp_description")
    private String description;

    /**
     * Hình ảnh sản phẩm
     */
    @Column(name = "sp_image")
    private String image;

    /**
     * Số lượng tồn kho
     */
    @Column(name = "sp_stock")
    private int stock;

    /**
     * Thương hiệu của sản phẩm
     */
    @ManyToOne
    @JoinColumn(name = "sp_brand_id")
    private Brand brand;

    /**
     * Danh mục của sản phẩm
     */
    @ManyToOne
    @JoinColumn(name = "sp_category_id")
    private Category category;
}
