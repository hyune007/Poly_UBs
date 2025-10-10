package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NhapKho")
public class GoodsImport {
    @Id
    @Column(name = "nk_id")
    private String id;

    @Column(name = "nk_quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    @Column(name = "nk_date")
    private Date date;
}
