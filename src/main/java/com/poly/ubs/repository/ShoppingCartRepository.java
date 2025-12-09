package com.poly.ubs.repository;

import com.poly.ubs.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể ShoppingCart (Giỏ hàng).
 */
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

    /**
     * Tìm tất cả các mục sản phẩm trong giỏ hàng của một khách hàng cụ thể.
     *
     * @param customerId Mã khách hàng.
     * @return Danh sách các mục sản phẩm trong giỏ hàng.
     */
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.id = :customerId")
    List<ShoppingCart> findByCustomerId(@Param("customerId") String customerId);

    /**
     * Tìm một mục sản phẩm cụ thể trong giỏ hàng của khách hàng.
     *
     * @param customerId Mã khách hàng.
     * @param productId  Mã sản phẩm.
     * @return Đối tượng ShoppingCart nếu tìm thấy, ngược lại trả về null.
     */
    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.id = :customerId AND sc.product.id = :productId")
    ShoppingCart findByCustomerIdAndProductId(@Param("customerId") String customerId, @Param("productId") String productId);
}
