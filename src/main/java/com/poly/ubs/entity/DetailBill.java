package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietHoaDon")
public class DetailBill {
    @Id
    @Column(name = "hdct_id", length = 8, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "hd_id", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "sp_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
