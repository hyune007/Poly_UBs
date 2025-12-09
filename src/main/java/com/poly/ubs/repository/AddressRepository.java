package com.poly.ubs.repository;

import com.poly.ubs.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Giao diện Repository quản lý các thao tác cơ sở dữ liệu đối với thực thể Address (Địa chỉ).
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

}
