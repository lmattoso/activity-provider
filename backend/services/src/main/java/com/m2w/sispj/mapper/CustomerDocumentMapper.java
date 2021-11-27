package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.CustomerDocument;
import com.m2w.sispj.dto.customer.CustomerDocumentDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CustomerDocumentMapper {

    static CustomerDocumentMapper getInstance() {
        return CustomerDocumentMapperInstance.INSTANCE;
    }

    final class CustomerDocumentMapperInstance {
        private static final CustomerDocumentMapper INSTANCE = Mappers.getMapper(CustomerDocumentMapper.class);
        private CustomerDocumentMapperInstance() { }
    }

    CustomerDocumentDTO entityToDTO(CustomerDocument customerDocument);

    @InheritInverseConfiguration
    CustomerDocument dtoToEntity(CustomerDocumentDTO customerDocumentDTO);
}
