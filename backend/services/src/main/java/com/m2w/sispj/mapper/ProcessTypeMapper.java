package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.ProcessType;
import com.m2w.sispj.dto.ProcessTypeDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ProcessTypeMapper {

    static ProcessTypeMapper getInstance() {
        return ProcessTypeMapperInstance.INSTANCE;
    }

    final class ProcessTypeMapperInstance {
        private static final ProcessTypeMapper INSTANCE = Mappers.getMapper(ProcessTypeMapper.class);
        private ProcessTypeMapperInstance() { }
    }

    ProcessTypeDTO entityToDTO(ProcessType entity);

    @InheritInverseConfiguration
    ProcessType dtoToEntity(ProcessTypeDTO dto);
}