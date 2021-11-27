package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.CustomerProcessDocument;
import com.m2w.sispj.domain.customer.CustomerProcessStep;
import com.m2w.sispj.dto.customer.CustomerProcessDocumentDTO;
import com.m2w.sispj.dto.customer.CustomerProcessStepDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CustomerProcessMapper {

    static CustomerProcessMapper getInstance() {
        return CustomerProcessMapperInstance.INSTANCE;
    }

    final class CustomerProcessMapperInstance {
        private static final CustomerProcessMapper INSTANCE = Mappers.getMapper(CustomerProcessMapper.class);
        private CustomerProcessMapperInstance() { }
    }

    List<CustomerProcessStepDTO> stepEntitiesToDTO(List<CustomerProcessStep> entities);

    List<CustomerProcessDocumentDTO> documentEntitiesToDTO(List<CustomerProcessDocument> entities);
}