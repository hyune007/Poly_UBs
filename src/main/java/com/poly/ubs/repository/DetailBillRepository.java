package com.poly.ubs.repository;

import com.poly.ubs.entity.DetailBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể DetailBill
 */
@Repository
public interface DetailBillRepository extends JpaRepository<DetailBill, String> {
}
