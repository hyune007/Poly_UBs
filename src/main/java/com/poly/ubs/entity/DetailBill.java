package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Thực thể chi tiết hóa đơn
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chitiethoadon")
public class DetailBill {
    /**
     * ID của chi tiết hóa đơn
     */
    @Id
    @Column(name = "hdct_id")
    private String id;

    /**
     * Hóa đơn liên quan
     */
    @ManyToOne
    @JoinColumn(name = "hd_id")
    private Bill bill;

    /**
     * Sản phẩm trong hóa đơn
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    /**
     * Số lượng sản phẩm
     */
    @Column(name = "quantity")
    private int quantity;
}
