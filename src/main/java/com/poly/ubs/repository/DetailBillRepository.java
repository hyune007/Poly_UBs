package com.poly.ubs.repository;

import com.poly.ubs.entity.DetailBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho thực thể DetailBill
 */
@Repository
public interface DetailBillRepository extends JpaRepository<DetailBill, String> {

    /**
     * Tìm tất cả chi tiết hóa đơn theo ID hóa đơn
     *
     * @param billId ID hóa đơn
     * @return danh sách chi tiết hóa đơn
     */
    List<DetailBill> findByBillId(String billId);
}
