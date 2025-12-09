package com.poly.ubs.repository;

import com.poly.ubs.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Brand (Thương hiệu).
 */
@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {

}