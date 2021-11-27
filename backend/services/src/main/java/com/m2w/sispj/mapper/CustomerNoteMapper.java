package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.CustomerNote;
import com.m2w.sispj.dto.customer.CustomerNoteDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CustomerNoteMapper {

    static CustomerNoteMapper getInstance() {
        return CustomerNoteMapperInstance.INSTANCE;
    }

    final class CustomerNoteMapperInstance {
        private static final CustomerNoteMapper INSTANCE = Mappers.getMapper(CustomerNoteMapper.class);
        private CustomerNoteMapperInstance() { }
    }

    CustomerNoteDTO entityToDTO(CustomerNote customerNote);

    @InheritInverseConfiguration
    CustomerNote dtoToEntity(CustomerNoteDTO customerNoteDTO);
}
