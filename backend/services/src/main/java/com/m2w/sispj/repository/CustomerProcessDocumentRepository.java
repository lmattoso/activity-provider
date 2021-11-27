package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerProcessDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProcessDocumentRepository extends JpaRepository<CustomerProcessDocument, Long> {
    List<CustomerProcessDocument> findByProcessIdOrderByTypeName(Long processId);
    List<CustomerProcessDocument> findByProcessCustomerId(Long customerId);
    void deleteByProcessCustomerId(Long customerId);
}