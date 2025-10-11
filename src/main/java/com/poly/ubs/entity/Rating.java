package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="DanhGia")
public class Rating {
    @Id
    private String dg_id;
    private String dg_content;
    private Integer dg_rating;
    private Date dg_date;
    @ManyToOne
    @JoinColumn(name="kh_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="sp_id")
    private Product product;
}
