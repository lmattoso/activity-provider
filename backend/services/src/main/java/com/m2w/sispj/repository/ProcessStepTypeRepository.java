package com.m2w.sispj.repository;

import com.m2w.sispj.domain.ProcessStepType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessStepTypeRepository extends JpaRepository<ProcessStepType, Long> {
    List<ProcessStepType> findAllByOrderByNameAsc(Pageable pageable);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String email, Long id);
}