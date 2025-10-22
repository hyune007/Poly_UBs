package com.poly.ubs.repository;

import com.poly.ubs.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
