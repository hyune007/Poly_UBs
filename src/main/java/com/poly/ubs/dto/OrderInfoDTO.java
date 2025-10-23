package com.poly.ubs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO cho thông tin đơn hàng
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    private String gender;
    private String fullName;
    private String phone;
    private String city;
    private String ward;
    private String detailAddress;
    private String note;
    private String deliveryType;
    private String shippingMethod;
    private int shippingFee;
    private int totalAmount;
}

