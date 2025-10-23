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
@Table(name = "diachi")
public class Address {
    /**
     * ID của địa chỉ
     */
    @Id
    @Column(name = "dc_id", length = 8)
    private String id;

    /**
     * Thành phố
     */
    @Column(name = "dc_city", length = 50)
    private String city;

    /**
     * Phường/xã
     */
    @Column(name = "dc_ward", length = 50)
    private String ward;

    @Column(name = "dc_detailaddress", length = 255)
    private String detailAddress;
    /**
     * Khách hàng sở hữu địa chỉ này
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}