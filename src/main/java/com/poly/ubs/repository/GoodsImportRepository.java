package com.poly.ubs.repository;

import com.poly.ubs.entity.GoodsImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể GoodsImport
 */
@Repository
public interface GoodsImportRepository extends JpaRepository<GoodsImport, String> {
}
