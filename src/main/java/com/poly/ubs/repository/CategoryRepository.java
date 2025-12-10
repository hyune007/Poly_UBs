package com.poly.ubs.repository;

import com.poly.ubs.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Category (Danh mục sản phẩm).
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}