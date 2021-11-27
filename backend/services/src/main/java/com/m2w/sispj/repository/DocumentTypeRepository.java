package com.m2w.sispj.repository;

import com.m2w.sispj.domain.DocumentType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {
    List<DocumentType> findAllByOrderByNameAsc(Pageable pageable);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String email, Long id);
}