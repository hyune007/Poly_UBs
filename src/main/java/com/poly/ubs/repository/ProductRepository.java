package com.poly.ubs.repository;

import com.poly.ubs.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    /**
     * Tìm sản phẩm theo ID danh mục với phân trang
     *
     * @param categoryId ID danh mục
     * @param pageable   thông tin phân trang
     * @return Page chứa danh sách sản phẩm
     */
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    /**
     * Tìm sản phẩm theo tên danh mục với phân trang
     *
     * @param categoryName tên danh mục
     * @param pageable     thông tin phân trang
     * @return Page chứa danh sách sản phẩm
     */
    Page<Product> findByCategoryName(String categoryName, Pageable pageable);

    /**
     * Đếm số lượng sản phẩm theo tên danh mục
     *
     * @param categoryName tên danh mục
     * @return số lượng sản phẩm
     */
    long countByCategory_Name(String categoryName);
//    Page<Product> findByCategory(String categoryName, Pageable pageable);
//    Page<Product> findAll(Pageable pageable);
//    long countByCategory(String categoryName);


    // Tìm theo tên sản phẩm
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Tìm theo category + tên sản phẩm
    Page<Product> findByCategory_IdAndNameContainingIgnoreCase(String categoryId, String name, Pageable pageable);


//    -----------
}
