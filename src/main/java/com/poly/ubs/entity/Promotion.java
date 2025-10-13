package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Thực thể khuyến mãi
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khuyenmai")
public class Promotion {
    /**
     * ID của khuyến mãi
     */
    @Id
    @Column(name = "km_id")
    private String id;
    
    /**
     * Tên khuyến mãi
     */
    @Column(name = "km_name")
    private String name;
    
    /**
     * Mô tả khuyến mãi
     */
    @Column(name = "km_description")
    private String description;
    
    /**
     * Phần trăm giảm giá
     */
    @Column(name = "km_percent")
    private Integer percent;
    
    /**
     * Ngày bắt đầu khuyến mãi
     */
    @Column(name = "km_start_date")
    private Date startDate;
    
    /**
     * Ngày kết thúc khuyến mãi
     */
    @Column(name = "km_end_date")
    private Date endDate;
}