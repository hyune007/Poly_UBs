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
@Table(name="KhuyenMai")
public class Promotion {
    @Id
    private String km_id;
    private String km_name;
    private String km_description;
    private Integer km_percent;
    private Date km_start_date;
    private Date km_end_date;
    @ManyToOne
    @JoinColumn(name="km_sp_id")
    private Product product;
}
