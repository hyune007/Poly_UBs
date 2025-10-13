package com.poly.ubs.repository;

import com.poly.ubs.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository cho thực thể Customer
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByEmailAndPassword(String email, String password);
}