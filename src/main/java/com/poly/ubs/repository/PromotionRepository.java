package com.poly.ubs.repository;

import com.poly.ubs.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Promotion
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
}
