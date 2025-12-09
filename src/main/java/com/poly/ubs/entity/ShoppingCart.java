package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho mục sản phẩm trong giỏ hàng.
 * Ánh xạ tới bảng "giohang" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "giohang")
public class ShoppingCart {
    /**
     * Mã định danh duy nhất của mục giỏ hàng.
     */
    @Id
    @Column(name = "gh_id")
    private String id;

    /**
     * Số lượng sản phẩm được chọn.
     */
    @Column(name = "sp_quantity")
    private int quantity;

    /**
     * Khách hàng sở hữu mục giỏ hàng này.
     */
    @OneToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    /**
     * Sản phẩm được thêm vào giỏ.
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;
}