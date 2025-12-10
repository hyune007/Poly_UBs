package com.poly.ubs.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho chi tiết đơn hàng (sản phẩm trong hóa đơn).
 * Ánh xạ tới bảng "chitiethoadon" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chitiethoadon")
public class DetailBill {
    /**
     * Mã định danh duy nhất của chi tiết hóa đơn.
     */
    @Id
    @Column(name = "hdct_id")
    private String id;

    /**
     * Hóa đơn chứa chi tiết này.
     */
    @ManyToOne
    @JoinColumn(name = "hd_id")
    @JsonBackReference
    private Bill bill;

    /**
     * Sản phẩm được đặt mua.
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    /**
     * Số lượng sản phẩm đặt mua.
     */
    @Column(name = "quantity")
    private int quantity;

    /**
     * Tổng thành tiền của chi tiết này (đơn giá * số lượng).
     */
    @Column(name = "hdct_total")
    private int total;

}