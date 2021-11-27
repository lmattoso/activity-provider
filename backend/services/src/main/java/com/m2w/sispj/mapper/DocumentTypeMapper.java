package com.m2w.sispj.mapper;

import com.m2w.sispj.domain.DocumentType;
import com.m2w.sispj.dto.DocumentTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface DocumentTypeMapper {

    static DocumentTypeMapper getInstance() {
        return DocumentTypeMapperInstance.INSTANCE;
    }

    final class DocumentTypeMapperInstance {
        private static final DocumentTypeMapper INSTANCE = Mappers.getMapper(DocumentTypeMapper.class);
        private DocumentTypeMapperInstance() { }
    }

    DocumentTypeDTO entityToDTO(DocumentType entity);
    DocumentType dtoToEntity(DocumentTypeDTO dto);
    List<DocumentType> dtoToEntities(List<DocumentTypeDTO> dto);
}