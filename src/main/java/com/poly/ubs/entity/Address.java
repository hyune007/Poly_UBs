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
@Table(name = "DiaChi")
public class Address {
    @Id
    @Column(name = "dc_id", length = 8, nullable = false)
    private String id;
    
    @Column(name = "dc_city", length = 50, nullable = false)
    private String city;
    
    @Column(name = "dc_ward", length = 50, nullable = false)
    private String ward;

    @ManyToOne
    @JoinColumn(name = "kh_id", nullable = false)
    private Customer customer;
}