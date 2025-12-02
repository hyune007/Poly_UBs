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


    @Transient
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (detailAddress != null) sb.append(detailAddress);
        if (ward != null) sb.append(sb.length() > 0 ? ", " : "").append(ward);
        if (city != null) sb.append(sb.length() > 0 ? ", " : "").append(city);
        return sb.toString();
    }

    /**
     * Đánh dấu địa chỉ mặc định của khách hàng
     */
    @Column(name = "dc_is_default")
    private Boolean isDefault = false;
}