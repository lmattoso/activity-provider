package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProcessRepository extends JpaRepository<CustomerProcess, Long> {
    List<CustomerProcess> findByCustomerId(Long customerId);
    List<CustomerProcess> findByTypeId(Long typeId);
    void deleteByCustomerId(Long customerId);
}