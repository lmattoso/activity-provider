package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerProvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProvisionRepository extends JpaRepository<CustomerProvision, Long> {
    List<CustomerProvision> findByCustomerIdOrderByCreateDate(Long customerProvisionId);
    void deleteByCustomerId(Long customerProvisionId);
}