package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.ProcessStepType;
import com.m2w.sispj.dto.ProcessStepTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ProcessStepTypeMapper {

    static ProcessStepTypeMapper getInstance() {
        return ProcessStepTypeMapperInstance.INSTANCE;
    }

    final class ProcessStepTypeMapperInstance {
        private static final ProcessStepTypeMapper INSTANCE = Mappers.getMapper(ProcessStepTypeMapper.class);
        private ProcessStepTypeMapperInstance() { }
    }

    ProcessStepTypeDTO entityToDTO(ProcessStepType entity);
    ProcessStepType dtoToEntity(ProcessStepTypeDTO dto);
    List<ProcessStepType> dtoToEntities(List<ProcessStepTypeDTO> dto);
}