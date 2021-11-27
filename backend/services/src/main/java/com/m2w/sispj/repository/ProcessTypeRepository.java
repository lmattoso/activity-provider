package com.m2w.sispj.repository;

import com.m2w.sispj.domain.ProcessType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessTypeRepository extends JpaRepository<ProcessType, Long> {
    List<ProcessType> findAllByOrderByNameAscVersionNumberAsc(Pageable pageable);
    List<ProcessType> findAllByPublishDateNotNullAndDeletedFalse(Pageable pageable);
    boolean existsByNameAndDeleted(String name, boolean deleted);
    boolean existsByNameAndIdNotAndDeletedAndPublishDateNull(String name, Long id, boolean deleted);
    List<ProcessType> findByDocumentsId(Long documentId);
    List<ProcessType> findByStepsId(Long stepId);
    List<ProcessType> findByNameAndPublishDateNotNull(String name);
}