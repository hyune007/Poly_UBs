package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Lớp thực thể đại diện cho chương trình khuyến mãi.
 * Ánh xạ tới bảng "khuyenmai" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khuyenmai")
public class Promotion {
    /**
     * Mã định danh duy nhất của chương trình khuyến mãi.
     */
    @Id
    @Column(name = "km_id")
    private String id;

    /**
     * Tên chương trình khuyến mãi.
     */
    @Column(name = "km_name")
    private String name;

    /**
     * Mô tả chi tiết chương trình.
     */
    @Column(name = "km_description")
    private String description;

    /**
     * Tỷ lệ giảm giá (phần trăm).
     */
    @Column(name = "km_percent")
    private Integer percent;

    /**
     * Ngày bắt đầu áp dụng khuyến mãi.
     */
    @Column(name = "km_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    /**
     * Ngày kết thúc chương trình khuyến mãi.
     */
    @Column(name = "km_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
}