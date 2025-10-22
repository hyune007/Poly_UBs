package com.poly.ubs.repository;

import com.poly.ubs.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Rating
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
}
