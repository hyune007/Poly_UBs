package com.poly.ubs.repository;

import com.poly.ubs.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}