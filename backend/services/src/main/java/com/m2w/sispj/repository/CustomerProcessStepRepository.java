package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerProcessStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProcessStepRepository extends JpaRepository<CustomerProcessStep, Long> {
    List<CustomerProcessStep> findByProcessIdOrderByOrder(Long processId);

    List<CustomerProcessStep> findByProcessCustomerId(Long customerId);

    void deleteByProcessCustomerId(Long customerId);
}