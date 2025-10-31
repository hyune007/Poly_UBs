package com.poly.ubs.service;

import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Cài đặt dịch vụ cho thực thể Product sử dụng dịch vụ chung
 */
@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, String, ProductRepository> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    protected ProductRepository getRepository() {
        return productRepository;
    }

    /**
     * Tìm sản phẩm theo ID danh mục với phân trang
     *
     * @param categoryId ID danh mục
     * @param pageable   thông tin phân trang
     * @return Page chứa danh sách sản phẩm
     */
    public Page<Product> findByCategoryId(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId (categoryId, pageable);
    }

    /**
     * Tìm tất cả sản phẩm với phân trang
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách sản phẩm
     */
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll (pageable);
    }

    /**
     * Tìm sản phẩm theo tên danh mục với phân trang
     *
     * @param categoryName tên danh mục
     * @param pageable     thông tin phân trang
     * @return Page chứa danh sách sản phẩm
     */
    public Page<Product> findByCategoryName(String categoryName, Pageable pageable) {
        return productRepository.findByCategoryName (categoryName, pageable);
    }

    /**
     * Đếm số lượng sản phẩm theo tên danh mục
     *
     * @param categoryName tên danh mục
     * @return số lượng sản phẩm
     */
    public long countByCategoryName(String categoryName) {
        return productRepository.countByCategory_Name (categoryName);
    }
    public Page<Product> findByCategoryAndName(String categoryId, String keyword, Pageable pageable) {
        return productRepository.findByCategory_IdAndNameContainingIgnoreCase(categoryId, keyword, pageable);
    }

    public Page<Product> findByNameContaining(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    // Method tìm theo khoảng giá với phân trang
    public Page<Product> findByPrice(double min, double max, Pageable pageable) {
        return productRepository.findByPriceBetween(min, max, pageable);
    }

    public Page<Product> findByPriceAndBrand(double min, double max, String brandId, Pageable pageable) {
        if (brandId != null && !brandId.isEmpty()) {
            return productRepository.findByPriceBetweenAndBrandId(min, max, brandId, pageable);
        } else {
            return productRepository.findByPriceBetween(min, max, pageable);
        }

    }
}