package com.poly.ubs.service;

import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import com.poly.ubs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // 👇 Thêm triển khai
    @Override
    public List<Product> findByCategory(String category) {
        return repository.findByCategory(category);
    }
}
