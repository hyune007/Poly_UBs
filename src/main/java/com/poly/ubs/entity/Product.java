package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho sản phẩm.
 * Ánh xạ tới bảng "sanpham" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sanpham")
public class Product {
    /**
     * Mã định danh duy nhất của sản phẩm.
     */
    @Id
    @Column(name = "sp_id", length = 8)
    private String id;

    /**
     * Tên sản phẩm.
     */
    @Column(name = "sp_name", length = 100)
    private String name;

    /**
     * Giá bán của sản phẩm.
     */
    @Column(name = "sp_price")
    private int price;

    /**
     * Mô tả chi tiết về sản phẩm.
     */
    @Column(name = "sp_description", length = 100)
    private String description;

    /**
     * Đường dẫn hoặc tên file hình ảnh của sản phẩm.
     */
    @Column(name = "sp_image", length = 100)
    private String image;

    /**
     * Số lượng hàng tồn kho.
     */
    @Column(name = "sp_stock")
    private int stock;

    /**
     * Thương hiệu của sản phẩm.
     */
    @ManyToOne
    @JoinColumn(name = "sp_brand_id")
    private Brand brand;

    /**
     * Danh mục (loại) của sản phẩm.
     */
    @ManyToOne
    @JoinColumn(name = "sp_category_id")
    private Category category;
}
