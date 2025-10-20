package com.poly.ubs.repository;

import com.poly.ubs.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory_Name(String categoryName, Pageable pageable);
    long countByCategory_Name(String categoryName);
}
