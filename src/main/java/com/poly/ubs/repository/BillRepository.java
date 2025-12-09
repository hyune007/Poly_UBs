package com.poly.ubs.repository;

import com.poly.ubs.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Bill (Hóa đơn).
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    /**
     * Tìm danh sách hóa đơn dựa trên mã khách hàng.
     *
     * @param customerId Mã khách hàng cần tìm.
     * @return Danh sách hóa đơn của khách hàng.
     */
    List<Bill> findByCustomerId(String customerId);

    /**
     * Tìm danh sách hóa đơn theo trạng thái và được tạo trước thời điểm chỉ định.
     *
     * @param status Trạng thái của hóa đơn.
     * @param date   Thời điểm mốc để so sánh.
     * @return Danh sách hóa đơn thỏa mãn điều kiện.
     */
    List<Bill> findByStatusAndDateBefore(String status, java.util.Date date);
}
