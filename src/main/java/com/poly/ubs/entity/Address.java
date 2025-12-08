package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho địa chỉ giao hàng của khách hàng.
 * Ánh xạ tới bảng "diachi" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diachi")
public class Address {
    /**
     * Mã định danh duy nhất của địa chỉ.
     */
    @Id
    @Column(name = "dc_id", length = 8)
    private String id;

    /**
     * Tên thành phố hoặc tỉnh.
     */
    @Column(name = "dc_city", length = 50)
    private String city;

    /**
     * Tên phường hoặc xã.
     */
    @Column(name = "dc_ward", length = 50)
    private String ward;

    /**
     * Địa chỉ chi tiết (số nhà, tên đường).
     */
    @Column(name = "dc_detailaddress", length = 255)
    private String detailAddress;

    /**
     * Khách hàng sở hữu địa chỉ này.
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;


    /**
     * Trả về chuỗi địa chỉ đầy đủ được định dạng từ các thành phần chi tiết, phường/xã và thành phố.
     *
     * @return Chuỗi địa chỉ đầy đủ.
     */
    @Transient
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (detailAddress != null) sb.append(detailAddress);
        if (ward != null) sb.append(sb.length() > 0 ? ", " : "").append(ward);
        if (city != null) sb.append(sb.length() > 0 ? ", " : "").append(city);
        return sb.toString();
    }

    /**
     * Trạng thái xác định đây có phải là địa chỉ mặc định của khách hàng hay không.
     */
    @Column(name = "dc_is_default")
    private Boolean isDefault = false;
}