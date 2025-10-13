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
@Table(name = "GioHang")
public class ShoppingCart {
    @Id
    @Column(name = "gh_id")
    private String id;

    @Column(name = "sp_quantity")
    private int quantity;

    @OneToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;
}