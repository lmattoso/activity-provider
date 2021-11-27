package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.CustomerProcessNote;
import com.m2w.sispj.dto.customer.CustomerProcessNoteDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CustomerProcessNoteMapper {

    static CustomerProcessNoteMapper getInstance() {
        return CustomerProcessNoteMapperInstance.INSTANCE;
    }

    final class CustomerProcessNoteMapperInstance {
        private static final CustomerProcessNoteMapper INSTANCE = Mappers.getMapper(CustomerProcessNoteMapper.class);
        private CustomerProcessNoteMapperInstance() { }
    }

    CustomerProcessNoteDTO entityToDTO(CustomerProcessNote customerNote);

    @InheritInverseConfiguration
    CustomerProcessNote dtoToEntity(CustomerProcessNoteDTO customerNoteDTO);
}
