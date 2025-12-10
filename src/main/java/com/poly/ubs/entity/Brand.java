
package com.poly.ubs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lớp thực thể đại diện cho thương hiệu sản phẩm.
 * Ánh xạ tới bảng "hang" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hang")
public class Brand {
    /**
     * Mã định danh duy nhất của thương hiệu.
     */
    @Id
    @Column(name = "hang_id", length = 10)
    private String id;

    /**
     * Tên thương hiệu.
     */
    @Column(name = "hang_name", length = 50)
    private String name;
}
