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
@Table(name="NhapKho")
public class GoodsImport {
    @Id
    private String nk_id;
    private Integer nk_quantity;
    private Date nk_date;
    @ManyToOne
    @JoinColumn(name="sp_id")
    private Product product;
}
