package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNoteRepository extends JpaRepository<CustomerNote, Long> {
    List<CustomerNote> findByCustomerIdOrderByCreateDateDesc(Long customerId);
    void deleteByCustomerId(Long customerId);
}