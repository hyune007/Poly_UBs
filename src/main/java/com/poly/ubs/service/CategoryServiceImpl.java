package com.poly.ubs.service;

import com.poly.ubs.entity.Category;
import com.poly.ubs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến danh mục sản phẩm.
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String, CategoryRepository> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected CategoryRepository getRepository() {
        return categoryRepository;
    }

    /**
     * Lấy danh sách toàn bộ danh mục sản phẩm.
     *
     * @return Danh sách đối tượng Category.
     */
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}