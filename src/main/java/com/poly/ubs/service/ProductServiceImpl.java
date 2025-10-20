package com.poly.ubs.service.impl;

import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import com.poly.ubs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repo;

    @Override
    public Product save(Product entity) {
        return repo.save(entity);
    }

    @Override
    public Product update(Product entity) {
        return repo.save(entity);
    }

    @Override
    public Product findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public java.util.List<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public Page<Product> findByCategory(String categoryName, Pageable pageable) {
        return repo.findByCategory_Name(categoryName, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public long countByCategory(String categoryName) {
        return repo.countByCategory_Name(categoryName);
    }
}
