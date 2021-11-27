package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.dto.customer.CustomerDTO;
import com.m2w.sispj.dto.customer.CustomerGridDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CustomerMapper {

    static CustomerMapper getInstance() {
        return CustomerMapperInstance.INSTANCE;
    }

    final class CustomerMapperInstance {
        private static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
        private CustomerMapperInstance() { }
    }

    @Mapping(source = "maritalStatus.id", target = "maritalStatusId")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    CustomerDTO entityToDTO(Customer customer);

    @Mapping(source = "country.name", target = "countryName")
    CustomerGridDTO entityToGridDTO(Customer customer);

    @InheritInverseConfiguration
    Customer dtoToEntity(CustomerDTO customer);
}
