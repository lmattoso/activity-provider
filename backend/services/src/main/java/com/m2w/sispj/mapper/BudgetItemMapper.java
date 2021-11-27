package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.budget.BudgetItem;
import com.m2w.sispj.dto.budget.BudgetItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface BudgetItemMapper {

    static BudgetItemMapper getInstance() {
        return BudgetItemMapperInstance.INSTANCE;
    }

    final class BudgetItemMapperInstance {
        private static final BudgetItemMapper INSTANCE = Mappers.getMapper(BudgetItemMapper.class);
        private BudgetItemMapperInstance() { }
    }

    BudgetItem dtoToEntity(BudgetItemDTO budgetItem);
    List<BudgetItem> dtoToEntities(List<BudgetItemDTO> budgetItems);
}
