
package com.poly.ubs.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Lớp thực thể đại diện cho hóa đơn bán hàng.
 * Ánh xạ tới bảng "hoadon" trong cơ sở dữ liệu.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hoadon")
public class Bill {
    /**
     * Mã định danh duy nhất của hóa đơn.
     */
    @Id
    @Column(name = "hd_id", length = 8)
    private String id;

    /**
     * Ngày và giờ tạo hóa đơn.
     */
    @Column(name = "hd_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * Trạng thái hiện tại của hóa đơn (ví dụ: Chờ xác nhận, Đã thanh toán, Đã hủy).
     */
    @Column(name = "hd_status")
    private String status;

    /**
     * Khách hàng thực hiện đơn hàng này.
     */
    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    /**
     * Nhân viên phụ trách xử lý đơn hàng (nếu có).
     */
    @ManyToOne
    @JoinColumn(name = "nv_id")
    private Employee employee;

    /**
     * Địa chỉ giao hàng được chọn cho hóa đơn này.
     */
    @ManyToOne
    @JoinColumn(name = "dc_id")
    private Address address;

    /**
     * Phương thức thanh toán được sử dụng.
     */
    @Column(name = "payment_method")
    private String paymentMethod;

    /**
     * Danh sách chi tiết các sản phẩm trong hóa đơn.
     */
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DetailBill> billDetails;

}
