package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Thực thể nhập kho
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nhapkho")
public class GoodsImport {
    /**
     * ID của phiếu nhập kho
     */
    @Id
    @Column(name = "nk_id")
    private String id;

    /**
     * Số lượng nhập
     */
    @Column(name = "nk_quantity")
    private int quantity;

    /**
     * Sản phẩm được nhập
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    /**
     * Ngày nhập kho
     */
    @Column(name = "nk_date")
    private Date date;
}
