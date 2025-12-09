package com.poly.ubs.service;

import com.poly.ubs.entity.Product;
import com.poly.ubs.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Triển khai dịch vụ xử lý nghiệp vụ liên quan đến sản phẩm.
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
     * Tìm kiếm sản phẩm theo mã danh mục với phân trang.
     *
     * @param categoryId Mã danh mục.
     * @param pageable   Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findByCategoryId(String categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    /**
     * Lấy danh sách toàn bộ sản phẩm với phân trang.
     *
     * @param pageable Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Tìm kiếm sản phẩm theo tên danh mục với phân trang.
     *
     * @param categoryName Tên danh mục.
     * @param pageable     Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findByCategoryName(String categoryName, Pageable pageable) {
        return productRepository.findByCategoryName(categoryName, pageable);
    }

    /**
     * Đếm số lượng sản phẩm thuộc một danh mục.
     *
     * @param categoryName Tên danh mục.
     * @return Số lượng sản phẩm.
     */
    public long countByCategoryName(String categoryName) {
        return productRepository.countByCategory_Name(categoryName);
    }

    /**
     * Tìm kiếm sản phẩm trong danh mục cụ thể theo từ khóa (tên).
     *
     * @param categoryId Mã danh mục.
     * @param keyword    Từ khóa tìm kiếm tên sản phẩm.
     * @param pageable   Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findByCategoryAndName(String categoryId, String keyword, Pageable pageable) {
        return productRepository.findByCategory_IdAndNameContainingIgnoreCase(categoryId, keyword, pageable);
    }

    /**
     * Tìm kiếm sản phẩm theo tên chứa từ khóa.
     *
     * @param keyword  Từ khóa tìm kiếm.
     * @param pageable Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findByNameContaining(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    /**
     * Tìm kiếm sản phẩm trong khoảng giá xác định.
     *
     * @param min      Giá tối thiểu.
     * @param max      Giá tối đa.
     * @param pageable Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findByPrice(double min, double max, Pageable pageable) {
        return productRepository.findByPriceBetween(min, max, pageable);
    }

    /**
     * Tìm kiếm sản phẩm theo thương hiệu và khoảng giá.
     * Nếu không chỉ định thương hiệu, chỉ lọc theo giá.
     *
     * @param min      Giá tối thiểu.
     * @param max      Giá tối đa.
     * @param brandId  Mã thương hiệu.
     * @param pageable Thông tin phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    public Page<Product> findByPriceAndBrand(double min, double max, String brandId, Pageable pageable) {
        if (brandId != null && !brandId.isEmpty()) {
            return productRepository.findByPriceBetweenAndBrandId(min, max, brandId, pageable);
        } else {
            return productRepository.findByPriceBetween(min, max, pageable);
        }

    }
}
