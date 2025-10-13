package com.poly.ubs.service;

import com.poly.ubs.entity.Category;
import com.poly.ubs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of service for Category entity using the generic service
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String, CategoryRepository> {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    protected CategoryRepository getRepository() {
        return categoryRepository;
    }
}