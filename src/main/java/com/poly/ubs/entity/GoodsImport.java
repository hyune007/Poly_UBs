package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Lớp thực thể đại diện cho phiếu nhập kho hàng hóa.
 * Ánh xạ tới bảng "nhapkho" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nhapkho")
public class GoodsImport {
    /**
     * Mã định danh duy nhất của phiếu nhập.
     */
    @Id
    @Column(name = "nk_id")
    private String id;

    /**
     * Số lượng sản phẩm nhập vào.
     */
    @Column(name = "nk_quantity")
    private int quantity;

    /**
     * Sản phẩm được nhập kho.
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    /**
     * Ngày thực hiện nhập kho.
     */
    @Column(name = "nk_date")
    @Temporal(TemporalType.DATE)
    private Date date;
}
