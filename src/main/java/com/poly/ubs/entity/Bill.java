package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Thực thể hóa đơn
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoadon")
public class Bill {
    /**
     * ID của hóa đơn
     */
    @Id
    @Column(name = "hd_id", length = 8)
    private String id;

    /**
     * Ngày tạo hóa đơn
     */
    @Column(name = "hd_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Trạng thái hóa đơn
     */
    @Column(name = "hd_status")
    private String status;

    /**
     * Khách hàng liên quan đến hóa đơn
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    /**
     * Nhân viên xử lý hóa đơn
     */
    @ManyToOne
    @JoinColumn(name = "nv_id")
    private Employee employee;

    /**
     * Địa chỉ giao hàng
     */
    @ManyToOne
    @JoinColumn(name = "dc_id")
    private Address address;
}