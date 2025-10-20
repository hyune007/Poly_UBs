package com.poly.ubs.service;

import com.poly.ubs.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends IGenericService<Product, Long> {
    Page<Product> findByCategory(String categoryName, Pageable pageable);
    Page<Product> findAll(Pageable pageable);
    long countByCategory(String categoryName);
}
