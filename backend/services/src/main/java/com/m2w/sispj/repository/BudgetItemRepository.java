package com.m2w.sispj.repository;

import com.m2w.sispj.domain.budget.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {
    void deleteByBudgetId(Long budgetId);
}