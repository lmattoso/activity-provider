package com.m2w.sispj.service;

import com.m2w.sispj.domain.DocumentType;
import com.m2w.sispj.dto.DocumentTypeDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.DocumentTypeMapper;
import com.m2w.sispj.repository.CustomerDocumentRepository;
import com.m2w.sispj.repository.DocumentTypeRepository;
import com.m2w.sispj.repository.ProcessTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentTypeService {

    private final ProcessTypeRepository processTypeRepository;
    private final DocumentTypeRepository repository;
    private final CustomerDocumentRepository customerDocumentRepository;

    public DocumentTypeService(ProcessTypeRepository processTypeRepository, DocumentTypeRepository repository, CustomerDocumentRepository customerDocumentRepository) {
        this.processTypeRepository = processTypeRepository;
        this.repository = repository;
        this.customerDocumentRepository = customerDocumentRepository;
    }

    public List<DocumentTypeDTO> list(Pageable pageable) {
        return repository.findAllByOrderByNameAsc(pageable).stream()
            .map(DocumentTypeMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    public DocumentTypeDTO findById(Long documentTypeId) {
        return repository.findById(documentTypeId)
            .map(DocumentTypeMapper.getInstance()::entityToDTO)
            .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.DOCUMENT_TYPE_NOT_EXISTS));
    }

    @Transactional
    public DocumentTypeDTO create(DocumentTypeDTO documentTypeDTO) {
        this.validateCreate(documentTypeDTO);

        DocumentType documentType = repository.saveAndFlush(
            DocumentTypeMapper.getInstance().dtoToEntity(documentTypeDTO));

        return DocumentTypeMapper.getInstance().entityToDTO(documentType);
    }

    @Transactional
    public DocumentTypeDTO update(DocumentTypeDTO documentTypeDTO) {
        DocumentType documentType = this.validateUpdate(documentTypeDTO);

        this.updateProcessTypeAttributes(documentType, documentTypeDTO);
        repository.saveAndFlush(documentType);

        return DocumentTypeMapper.getInstance().entityToDTO(documentType);
    }

    private void validateCreate(DocumentTypeDTO documentTypeDTO) {
        if(!ObjectUtils.isEmpty(documentTypeDTO.getName()) && repository.existsByName(documentTypeDTO.getName())) {
            throw new SISPJException(SISPJExceptionDefinition.DOCUMENT_TYPE_DUPLICATED_NAME);
        }
    }

    private DocumentType validateUpdate(DocumentTypeDTO documentTypeDTO) {
        Optional<DocumentType> documentType = repository.findById(documentTypeDTO.getId());

        if(documentType.isEmpty()) {
            throw new SISPJException(SISPJExceptionDefinition.DOCUMENT_TYPE_NOT_EXISTS);
        }

        if(!ObjectUtils.isEmpty(documentTypeDTO.getName()) && repository.existsByNameAndIdNot(documentTypeDTO.getName(), documentTypeDTO.getId())) {
            throw new SISPJException(SISPJExceptionDefinition.DOCUMENT_TYPE_DUPLICATED_NAME);
        }

        return documentType.get();
    }

    private void updateProcessTypeAttributes(DocumentType documentType, DocumentTypeDTO documentTypeDTO) {
        documentType.setName(documentTypeDTO.getName());
    }

    @Transactional
    public void delete(Long documentId) {
        boolean isUsed = !processTypeRepository.findByDocumentsId(documentId).isEmpty() ||
            !customerDocumentRepository.findByTypeId(documentId).isEmpty();

        if(isUsed) {
            throw new SISPJException(SISPJExceptionDefinition.DOCUMENT_TYPE_IS_USED);
        }

        repository.deleteById(documentId);
    }
}