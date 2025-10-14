package com.poly.ubs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Thực thể thương hiệu
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hang")
public class Brand {
    /**
     * ID của thương hiệu
     */
    @Id
    @Column(name = "hang_id", length = 10)
    private String id;
    
    /**
     * Tên thương hiệu
     */
    @Column(name = "hang_name", length = 50)
    private String name;
}