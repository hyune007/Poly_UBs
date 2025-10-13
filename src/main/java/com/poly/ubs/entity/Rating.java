package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Thực thể đánh giá
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "danhgia")
public class Rating {
    /**
     * ID của đánh giá
     */
    @Id
    @Column(name = "dg_id")
    private String id;

    /**
     * Nội dung đánh giá
     */
    @Column(name = "dg_content")
    private String content;

    /**
     * Số sao đánh giá (1-5)
     */
    @Column(name = "dg_rating")
    private int rating;

    /**
     * Ngày đánh giá
     */
    @Column(name = "dg_date")
    private Date date;

    /**
     * Sản phẩm được đánh giá
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    /**
     * Khách hàng thực hiện đánh giá
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}