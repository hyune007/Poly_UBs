package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Lớp thực thể đại diện cho đánh giá sản phẩm của khách hàng.
 * Ánh xạ tới bảng "danhgia" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "danhgia")
public class Rating {
    /**
     * Mã định danh duy nhất của đánh giá.
     */
    @Id
    @Column(name = "dg_id")
    private String id;

    /**
     * Nội dung bình luận/nhận xét.
     */
    @Column(name = "dg_content")
    private String content;

    /**
     * Điểm số đánh giá (thang điểm 1-5).
     */
    @Column(name = "dg_rating")
    private int rating;

    /**
     * Ngày thực hiện đánh giá.
     */
    @Column(name = "dg_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Sản phẩm được đánh giá.
     */
    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    /**
     * Khách hàng thực hiện đánh giá.
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}