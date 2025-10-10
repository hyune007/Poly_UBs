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
    @Column(name = "hdct_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "hd_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;
}
