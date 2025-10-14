package com.poly.ubs.service;

import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Ví dụ cài đặt dịch vụ sử dụng dịch vụ chung
 */
@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, String, ProductRepository> {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    protected ProductRepository getRepository() {
        return productRepository;
    }
}
