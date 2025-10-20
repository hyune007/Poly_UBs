package com.poly.ubs.service;

import com.poly.ubs.entity.Category;
import com.poly.ubs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Cài đặt dịch vụ cho Category sử dụng GenericServiceImpl
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String, CategoryRepository>
        implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected CategoryRepository getRepository() {
        return categoryRepository;
    }
}
