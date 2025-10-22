package com.poly.ubs.service;

import com.poly.ubs.entity.Category;
import com.poly.ubs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Cài đặt dịch vụ cho thực thể Category sử dụng dịch vụ chung
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String, CategoryRepository> {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    protected CategoryRepository getRepository() {
        return categoryRepository;
    }
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}