package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Thực thể địa chỉ
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DiaChi")
public class Address {
    /**
     * ID của địa chỉ
     */
    @Id
    @Column(name = "dc_id")
    private String id;
    
    /**
     * Thành phố
     */
    @Column(name = "dc_city")
    private String city;
    
    /**
     * Phường/xã
     */
    @Column(name = "dc_ward")
    private String ward;

    /**
     * Khách hàng sở hữu địa chỉ này
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}