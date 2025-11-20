package com.poly.ubs.repository;

import com.poly.ubs.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho thực thể Bill
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    List<Bill> findByCustomerId(String customerId);

}
