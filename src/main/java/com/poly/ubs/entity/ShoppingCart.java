package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Thực thể giỏ hàng
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "giohang")
public class ShoppingCart {
    /**
     * ID của mục trong giỏ hàng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "gh_id", length = 36)
    private String id;

    /**
     * Số lượng sản phẩm trong giỏ hàng
     */
    @Column(name = "sp_quantity")
    private int quantity;

    /**
     * Khách hàng sở hữu giỏ hàng này
     */
    @OneToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    /**
     * Sản phẩm trong giỏ hàng
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;
}