package com.poly.ubs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Đối tượng chuyển giao dữ liệu (DTO) chứa thông tin đơn hàng.
 * Sử dụng để nhận dữ liệu từ form đặt hàng và chuyển đến tầng xử lý logic.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
//    /** Chuyển thông tin giới tính của người đặt hàng. */
//    private String gender;

    /**
     * Chuyển thông tin họ và tên đầy đủ của người nhận.
     */
    private String fullName;

    /**
     * Chuyển thông tin số điện thoại liên hệ.
     */
    private String phone;

    /**
     * Chuyển thông tin Tỉnh/Thành phố trong địa chỉ giao hàng.
     */
    private String city;

    /**
     * Chuyển thông tin Phường/Xã trong địa chỉ giao hàng.
     */
    private String ward;

    /**
     * Chuyển thông tin địa chỉ chi tiết (số nhà, đường...).
     */
    private String detailAddress;

    /**
     * Chuyển thông tin ghi chú thêm cho đơn hàng.
     */
    private String note;

    /**
     * Chuyển thông tin loại hình giao hàng (ví dụ: giao tại nhà, nhận tại cửa hàng).
     */
    private String deliveryType;

    /**
     * Chuyển thông tin phương thức vận chuyển.
     */
    private String shippingMethod;

    /**
     * Chuyển thông tin phí vận chuyển.
     */
    private int shippingFee;

    /**
     * Chuyển thông tin tổng giá trị đơn hàng cần thanh toán.
     */
    private int totalAmount;
}

