package com.poly.ubs.repository;

import com.poly.ubs.entity.GoodsImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể GoodsImport (Nhập kho).
 */
@Repository
public interface GoodsImportRepository extends JpaRepository<GoodsImport, String> {
}
