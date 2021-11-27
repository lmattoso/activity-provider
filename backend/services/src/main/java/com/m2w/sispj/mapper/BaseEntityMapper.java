package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.Country;
import com.m2w.sispj.domain.MaritalStatus;
import com.m2w.sispj.dto.SimpleEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseEntityMapper {

    static BaseEntityMapper getInstance() {
        return BaseEntityMapperInstance.INSTANCE;
    }

    final class BaseEntityMapperInstance {
        private static final BaseEntityMapper INSTANCE = Mappers.getMapper(BaseEntityMapper.class);
        private BaseEntityMapperInstance() { }
    }

    SimpleEntityDTO entityToDTO(MaritalStatus maritalStatus);
    SimpleEntityDTO entityToDTO(Country country);
}
