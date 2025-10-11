package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="GioHang")
public class ShoppingCart {
    @Id
    private String gh_id;
    private Integer sp_quantity;
    @ManyToOne
    @JoinColumn(name="kh_id", unique = true)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="sp_id")
    private Product product;
}
