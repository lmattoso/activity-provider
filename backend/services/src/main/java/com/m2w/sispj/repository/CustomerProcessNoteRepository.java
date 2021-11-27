package com.m2w.sispj.repository;

import com.m2w.sispj.domain.customer.CustomerProcessNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerProcessNoteRepository extends JpaRepository<CustomerProcessNote, Long> {
    List<CustomerProcessNote> findByProcessIdOrderByCreateDateDesc(Long processId);
    void deleteByProcessId(Long processId);
    void deleteByProcessCustomerId(Long customerId);
}