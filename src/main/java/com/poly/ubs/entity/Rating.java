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
@Table(name = "DanhGia")
public class Rating {
    @Id
    @Column(name = "dg_id")
    private String id;

    @Column(name = "dg_content")
    private String content;

    @Column(name = "dg_rating")
    private int rating;

    @Column(name = "dg_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}