package com.poly.ubs.repository;

import com.poly.ubs.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Brand
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {

}