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
    @Column(name = "dc_id")
    private String id;
    
    @Column(name = "dc_city")
    private String city;
    
    @Column(name = "dc_ward")
    private String ward;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}