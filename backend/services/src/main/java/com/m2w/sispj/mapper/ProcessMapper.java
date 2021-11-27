package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.CustomerProcess;
import com.m2w.sispj.dto.customer.CustomerProcessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ProcessMapper {

    static ProcessMapper getInstance() {
        return ServiceMapperInstance.INSTANCE;
    }

    final class ServiceMapperInstance {
        private static final ProcessMapper INSTANCE = Mappers.getMapper(ProcessMapper.class);
        private ServiceMapperInstance() { }
    }

    @Mapping(source = "status.description", target = "statusName")
    CustomerProcessDTO entityToDTO(CustomerProcess customerProcess);

    CustomerProcess dtoToEntity(CustomerProcessDTO serviceTypeDTO);
    List<CustomerProcess> dtosToEntities(List<CustomerProcessDTO> serviceTypeDTO);
}