package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="DiaChi")
public class Address {
    @Id
    private String dc_id;
    private String dc_ward;
    private String dc_city;
    @ManyToOne
    @JoinColumn(name="kh_id")
    private Customer customer;
    @OneToMany(mappedBy = "address")
    private List<Bill> bills;
}
