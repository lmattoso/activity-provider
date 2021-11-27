package com.m2w.sispj.repository;

import com.m2w.sispj.domain.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCustomerIdOrderByCreateDateDesc(Long customerId, Pageable pageable);
    List<Activity> findByProcessIdOrderByCreateDateDesc(Long customerId, Pageable pageable);
    void deleteByCustomerId(Long customerId);
}