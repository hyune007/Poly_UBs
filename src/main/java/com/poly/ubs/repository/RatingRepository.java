package com.poly.ubs.repository;

import com.poly.ubs.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Rating (Đánh giá).
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
}
