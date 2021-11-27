package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Long> {
    List<CustomerDocument> findByCustomerIdOrderByCreateDateDesc(Long customerId);
    void deleteByCustomerId(Long customerId);
    List<CustomerDocument> findByTypeId(Long documentId);
}