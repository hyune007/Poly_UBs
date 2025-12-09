package com.poly.ubs.repository;

import com.poly.ubs.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Product (Sản phẩm).
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    /**
     * Tìm danh sách sản phẩm theo mã danh mục có hỗ trợ phân trang.
     *
     * @param categoryId Mã danh mục sản phẩm.
     * @param pageable   Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    /**
     * Tìm danh sách sản phẩm theo tên danh mục có hỗ trợ phân trang.
     *
     * @param categoryName Tên danh mục sản phẩm.
     * @param pageable     Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    Page<Product> findByCategoryName(String categoryName, Pageable pageable);

    /**
     * Đếm tổng số lượng sản phẩm thuộc một danh mục cụ thể.
     *
     * @param categoryName Tên danh mục sản phẩm.
     * @return Số lượng sản phẩm.
     */
    long countByCategory_Name(String categoryName);

    /**
     * Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường) có hỗ trợ phân trang.
     *
     * @param name     Tên sản phẩm cần tìm.
     * @param pageable Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Tìm kiếm sản phẩm thuộc một danh mục cụ thể theo tên (không phân biệt hoa thường) có hỗ trợ phân trang.
     *
     * @param categoryId Mã danh mục sản phẩm.
     * @param name       Tên sản phẩm cần tìm.
     * @param pageable   Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    Page<Product> findByCategory_IdAndNameContainingIgnoreCase(String categoryId, String name, Pageable pageable);

    /**
     * Tìm kiếm sản phẩm trong khoảng giá xác định có hỗ trợ phân trang.
     *
     * @param min      Giá tối thiểu.
     * @param max      Giá tối đa.
     * @param pageable Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    Page<Product> findByPriceBetween(double min, double max, Pageable pageable);

    /**
     * Tìm kiếm sản phẩm theo thương hiệu trong khoảng giá xác định có hỗ trợ phân trang.
     *
     * @param min      Giá tối thiểu.
     * @param max      Giá tối đa.
     * @param brandId  Mã thương hiệu.
     * @param pageable Đối tượng phân trang.
     * @return Trang kết quả chứa danh sách sản phẩm.
     */
    Page<Product> findByPriceBetweenAndBrandId(double min, double max, String brandId, Pageable pageable);
}
