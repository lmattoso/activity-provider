package com.m2w.sispj.service;

import com.m2w.sispj.domain.User;
import com.m2w.sispj.domain.budget.Budget;
import com.m2w.sispj.domain.budget.BudgetItem;
import com.m2w.sispj.dto.budget.BudgetDTO;
import com.m2w.sispj.dto.budget.BudgetItemDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.BudgetItemMapper;
import com.m2w.sispj.mapper.BudgetMapper;
import com.m2w.sispj.repository.BudgetItemRepository;
import com.m2w.sispj.repository.BudgetRepository;
import com.m2w.sispj.util.SISPJUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final BudgetRepository repository;
    private final BudgetItemRepository budgetItemRepository;
    private final BudgetReportService budgetReportService;

    public BudgetService(BudgetRepository repository, BudgetItemRepository budgetItemRepository, BudgetReportService budgetReportService) {
        this.repository = repository;
        this.budgetItemRepository = budgetItemRepository;
        this.budgetReportService = budgetReportService;
    }

    public List<BudgetDTO> list(Pageable pageable) {
        return repository.findAll(pageable).stream().map(BudgetMapper.getInstance()::entityToDTO).collect(Collectors.toList());
    }

    public BudgetDTO findById(Long budgetId) {
        Optional<Budget> budget = repository.findById(budgetId);

        if(budget.isPresent()) {
            return BudgetMapper.getInstance().entityToDTO(budget.get());
        }

        throw new RuntimeException("Orçamento não existe!");
    }

    @Transactional
    public BudgetDTO create(BudgetDTO budgetDTO) {
        Budget budget = BudgetMapper.getInstance().dtoToEntity(budgetDTO);
        repository.saveAndFlush(budget);

        AtomicInteger order = new AtomicInteger();
        budget.getItems().forEach(budgetItem -> {
            budgetItem.setBudget(budget);
            budgetItem.setOrder(order.incrementAndGet());
        });

        budgetItemRepository.saveAll(budget.getItems());

        return BudgetMapper.getInstance().entityToDTO(budget);
    }

    @Transactional
    public BudgetDTO update(BudgetDTO budgetDTO) {
        Budget budget = this.validateUpdate(budgetDTO);

        this.updateBudgetAttributes(budget, budgetDTO);
        repository.saveAndFlush(budget);

        Map<Long, BudgetItem> itensMap = budget.getItems().stream().collect(Collectors.toMap(BudgetItem::getId, budgetItem -> budgetItem));

        int order = 0;

        for(BudgetItemDTO itemDTO : budgetDTO.getItems()) {
            if(itemDTO.getId() != null) {
                BudgetItem savedItem = itensMap.get(itemDTO.getId());
                itensMap.remove(itemDTO.getId());
                savedItem.setRequest(itemDTO.getRequest());
                savedItem.setValue(itemDTO.getValue());
                savedItem.setIva(itemDTO.getIva());
                savedItem.setOrder(++order);
                budgetItemRepository.saveAndFlush(savedItem);
            } else {
                BudgetItem budgetItem = BudgetItemMapper.getInstance().dtoToEntity(itemDTO);
                budgetItem.setBudget(budget);
                budgetItem.setOrder(++order);
                budgetItemRepository.saveAndFlush(budgetItem);
            }
        }

        budgetItemRepository.deleteInBatch(itensMap.values());

        return BudgetMapper.getInstance().entityToDTO(budget);
    }

    private Budget validateUpdate(BudgetDTO budgetDTO) {
        Optional<Budget> budget = repository.findById(budgetDTO.getId());

        if(budget.isEmpty()) {
            throw new SISPJException(SISPJExceptionDefinition.BUDGET_NOT_EXISTS);
        }

        return budget.get();
    }

    private void updateBudgetAttributes(Budget budget, BudgetDTO budgetDTO) {
        Budget newBudget = BudgetMapper.getInstance().dtoToEntity(budgetDTO);

        budget.setName(newBudget.getName());
        budget.setAddress(newBudget.getAddress());
        budget.setPhone(newBudget.getPhone());
        budget.setNif(newBudget.getNif());
        budget.setNiss(newBudget.getNiss());
        budget.setExpirationDate(newBudget.getExpirationDate());
        budget.setRequestDescription(newBudget.getRequestDescription());
        budget.setServiceDescription(newBudget.getServiceDescription());
        budget.setDocumentsDescription(newBudget.getDocumentsDescription());
        budget.setComments(newBudget.getComments());
    }

    public byte[] downloadReport(Long budgetId) {
        Optional<Budget> budget = repository.findById(budgetId);

        if(budget.isPresent()) {
            return budgetReportService.generateReport(budget.get());
        }

        throw new SISPJException(SISPJExceptionDefinition.BUDGET_NOT_EXISTS);
    }

    @Transactional
    public void delete(Long budgetId) {
        User currentUser = SISPJUtil.getCurrentUser();

        if(!currentUser.isAdmin()) {
            throw new SISPJException(SISPJExceptionDefinition.BUDGET_DELETE);
        }

        budgetItemRepository.deleteByBudgetId(budgetId);
        repository.deleteById(budgetId);
    }
}