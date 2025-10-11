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
@Table(name="ChiTietHoaDon")
public class DetailBill {
    @Id
    private String hdct_id;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="hd_id")
    private Bill bill;
    @ManyToOne
    @JoinColumn(name="sp_id")
    private Product product;
}
