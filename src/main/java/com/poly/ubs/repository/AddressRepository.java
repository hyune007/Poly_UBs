package com.poly.ubs.repository;

import com.poly.ubs.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByCustomerId(String customerId);
}
