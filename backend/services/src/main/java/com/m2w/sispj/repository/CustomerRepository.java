package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    boolean existsByNif(String nif);
    boolean existsByNiss(String niss);

    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByNifAndIdNot(String nif, Long id);
    boolean existsByNissAndIdNot(String niss, Long id);
}