package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.customer.CustomerProvision;
import com.m2w.sispj.dto.customer.CustomerProvisionDTO;
import com.m2w.sispj.util.SISPJUtil;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CustomerProvisionMapper {

    static CustomerProvisionMapper getInstance() {
        return CustomerNoteMapperInstance.INSTANCE;
    }

    final class CustomerNoteMapperInstance {
        private static final CustomerProvisionMapper INSTANCE = Mappers.getMapper(CustomerProvisionMapper.class);
        private CustomerNoteMapperInstance() { }
    }

    @Mapping(source = "paymentDate", target = "paymentDate", dateFormat = SISPJUtil.DATE_TIME_MASK)
    @Mapping(source = "process.id", target = "customerProcessId")
    CustomerProvisionDTO entityToDTO(CustomerProvision customerProvision);

    @InheritInverseConfiguration
    CustomerProvision dtoToEntity(CustomerProvisionDTO customerProvisionDTO);
}