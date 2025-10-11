package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="HoaDon")
public class Bill {
    @Id
    private String hd_id;
    private Date hd_date;
    private String hd_status;
    @ManyToOne
    @JoinColumn(name="kh_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name="nv_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name="dc_id")
    private Address address;
    @OneToMany(mappedBy = "bill")
    private List<DetailBill> detailBills;
}
