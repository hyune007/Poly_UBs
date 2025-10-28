package com.poly.ubs.repository;

import com.poly.ubs.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho thực thể ShoppingCart
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

    /**
     * Tìm tất cả sản phẩm trong giỏ hàng của khách hàng
     *
     * @param customerId ID khách hàng
     * @return danh sách giỏ hàng
     */
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.id = :customerId")
    List<ShoppingCart> findByCustomerId(@Param("customerId") String customerId);

    /**
     * Tìm sản phẩm cụ thể trong giỏ hàng của khách hàng
     *
     * @param customerId ID khách hàng
     * @param productId ID sản phẩm
     * @return giỏ hàng nếu tìm thấy, null nếu không
     */
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.id = :customerId AND sc.product.id = :productId")
    ShoppingCart findByCustomerIdAndProductId(@Param("customerId") String customerId, @Param("productId") String productId);
}
