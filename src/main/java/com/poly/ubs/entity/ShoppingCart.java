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
    @Column(name = "gh_id", length = 8, nullable = false)
    private String id;

    @Column(name = "sp_quantity", nullable = false)
    private int quantity;

    @OneToOne
    @JoinColumn(name = "kh_id", nullable = false, unique = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sp_id", nullable = false)
    private Product product;
}