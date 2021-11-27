package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.budget.Budget;
import com.m2w.sispj.dto.budget.BudgetDTO;
import com.m2w.sispj.util.SISPJUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface BudgetMapper {

    static BudgetMapper getInstance() {
        return BudgetMapperInstance.INSTANCE;
    }

    final class BudgetMapperInstance {
        private static final BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);
        private BudgetMapperInstance() { }
    }

    @Mapping(source = "expirationDate", target = "expirationDate", dateFormat = SISPJUtil.DATE_TIME_MASK)
    BudgetDTO entityToDTO(Budget customer);

    Budget dtoToEntity(BudgetDTO customer);
}
