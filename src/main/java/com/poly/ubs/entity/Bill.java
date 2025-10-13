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
@Table(name = "HoaDon")
public class Bill {
    @Id
    private String id;
    
    @Column(name = "hd_date")
    private Date date;
    
    @Column(name = "hd_status")
    private String status;
    
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "nv_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "dc_id")
    private Address address;
}