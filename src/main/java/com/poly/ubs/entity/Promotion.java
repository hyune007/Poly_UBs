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
@Table(name = "KhuyenMai")
public class Promotion {
    @Id
    @Column(name = "km_id")
    private String id;
    
    @Column(name = "km_name")
    private String name;
    
    @Column(name = "km_description")
    private String description;
    
    @Column(name = "km_percent")
    private Integer percent;
    
    @Column(name = "km_start_date")
    private Date startDate;
    
    @Column(name = "km_end_date")
    private Date endDate;
}