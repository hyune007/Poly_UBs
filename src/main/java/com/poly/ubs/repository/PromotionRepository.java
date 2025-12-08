package com.poly.ubs.repository;

import com.poly.ubs.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Promotion (Khuyến mãi).
 */
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, String> {
}
