package com.poly.ubs.repository;

import com.poly.ubs.entity.Address;
import com.poly.ubs.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Address
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    @Transactional
    void deleteByIdAndCustomer(String id, Customer customer);
}
