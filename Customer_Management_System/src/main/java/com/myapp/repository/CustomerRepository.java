package com.myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    
    List<Customer> findByFirstName(String firstName);
    @Query("SELECT c FROM Customer c WHERE " +
            "c.firstName LIKE %:keyword% OR " +
            "c.lastName LIKE %:keyword% OR " +
            "c.email LIKE %:keyword%")
     Page<Customer> searchCustomers(String keyword, Pageable pageable);
}
