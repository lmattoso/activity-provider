package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.Contact;
import com.m2w.sispj.dto.ContactDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ContactMapper {

    static ContactMapper getInstance() {
        return ContactMapperInstance.INSTANCE;
    }

    final class ContactMapperInstance {
        private static final ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);
        private ContactMapperInstance() { }
    }

    ContactDTO entityToDTO(Contact contact);

    @InheritInverseConfiguration
    Contact dtoToEntity(ContactDTO contactDTO);
}
