package com.m2w.sispj.repository;

import com.m2w.sispj.domain.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long> {
    List<MaritalStatus> findAllByOrderByNameAsc();
}