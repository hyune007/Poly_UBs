package com.poly.ubs.repository;

import com.poly.ubs.entity.DetailBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể DetailBill (Chi tiết hóa đơn).
 */
@Repository
public interface DetailBillRepository extends JpaRepository<DetailBill, String> {

    /**
     * Lấy danh sách chi tiết hóa đơn dựa trên mã hóa đơn.
     *
     * @param billId Mã hóa đơn.
     * @return Danh sách các chi tiết thuộc hóa đơn chỉ định.
     */
    List<DetailBill> findByBillId(String billId);
}
