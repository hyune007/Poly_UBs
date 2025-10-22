package com.poly.ubs.repository;

import com.poly.ubs.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể ShoppingCart
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
}
