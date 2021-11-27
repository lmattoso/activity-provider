package com.m2w.sispj.service;

import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.customer.CustomerDocument;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.dto.TemporaryLinkDTO;
import com.m2w.sispj.dto.customer.CustomerDocumentDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.CustomerDocumentMapper;
import com.m2w.sispj.repository.CustomerDocumentRepository;
import com.m2w.sispj.repository.CustomerRepository;
import com.m2w.sispj.repository.DocumentTypeRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerDocumentService {

    private final CustomerDocumentRepository repository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ActivityService activityService;
    private final CustomerRepository customerRepository;
    private final FileUploadService fileUploadService;

    public CustomerDocumentService(CustomerDocumentRepository repository,
                                   DocumentTypeRepository documentTypeRepository,
                                   ActivityService activityService,
                                   CustomerRepository customerRepository,
                                   FileUploadService fileUploadService) {
        this.repository = repository;
        this.documentTypeRepository = documentTypeRepository;
        this.activityService = activityService;
        this.customerRepository = customerRepository;
        this.fileUploadService = fileUploadService;
    }

    public List<CustomerDocumentDTO> findByCustomerId(Long customerId) {
        return repository.findByCustomerIdOrderByCreateDateDesc(customerId).stream()
            .map(CustomerDocumentMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public CustomerDocumentDTO create(CustomerDocumentDTO customerDocumentDTO) {
        Customer customer = customerRepository.findById(customerDocumentDTO.getCustomerId())
            .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.CUSTOMER_NOT_EXISTS));

        CustomerDocument customerDocument = repository.save(CustomerDocument.builder()
            .description(customerDocumentDTO.getDescription())
            .type(documentTypeRepository.findById(customerDocumentDTO.getType().getId()).orElse(null))
            .customer(customer)
            .name(customerDocumentDTO.getName())
            .key("")
            .build());

        customerDocument.setKey(FileUploadService.generateCustomerFileName(customer.getId(), customerDocument.getId(), customerDocument.getName()));
        repository.flush();

        activityService.create(customerDocument.getCustomer(), ActivityType.DOCUMENT_CREATED, customerDocument.getType().getName());

        CustomerDocumentDTO dto = CustomerDocumentMapper.getInstance().entityToDTO(customerDocument);
        dto.setUploadLink(fileUploadService.getTemporaryUploadLink(customerDocument.getKey()));

        return dto;
    }

    @Transactional
    public CustomerDocumentDTO update(CustomerDocumentDTO customerDocumentDTO) {
        return repository.findById(customerDocumentDTO.getId())
            .map(customerDocument -> {
                if(customerDocumentDTO.isChangedDocument()) {
                    fileUploadService.delete(customerDocument.getKey());
                    customerDocument.setName(customerDocumentDTO.getName());
                    customerDocument.setKey(FileUploadService.generateCustomerFileName(customerDocument.getCustomer().getId(), customerDocument.getId(), customerDocument.getName()));
                }

                customerDocument.setDescription(customerDocumentDTO.getDescription());
                customerDocument.setType(documentTypeRepository.findById(customerDocumentDTO.getType().getId()).orElse(null));
                repository.save(customerDocument);
                activityService.create(customerDocument.getCustomer(), ActivityType.DOCUMENT_UPDATED, customerDocument.getType().getName());

                CustomerDocumentDTO dto = CustomerDocumentMapper.getInstance().entityToDTO(customerDocument);
                dto.setUploadLink(fileUploadService.getTemporaryUploadLink(customerDocument.getKey()));

                return dto;
            })
            .orElse(null);
    }

    public void delete(Long customerNoteId) {
        repository.findById(customerNoteId)
            .ifPresent(customerNote -> {
                String key = customerNote.getKey();
                repository.deleteById(customerNoteId);
                if(!Strings.isBlank(key)) {
                    fileUploadService.delete(FileUploadService.generateCustomerDocumentPath(customerNote.getCustomer().getId(), customerNote.getId()));
                }
                activityService.create(customerNote.getCustomer(), ActivityType.DOCUMENT_DELETED, customerNote.getType().getName());
            });
    }

    public TemporaryLinkDTO getTemporaryLink(Long customerDocumentId) {
        return repository.findById(customerDocumentId)
            .map(document -> TemporaryLinkDTO.builder()
                .url(fileUploadService.getTemporaryDownloadableLink(document.getKey()))
                .build())
            .orElse(null);
    }

    public CustomerDocumentDTO upload(MultipartFile fileToUpload, Long customerDocumentId) {
        return repository.findById(customerDocumentId)
            .map(document -> {
                try {
                    String oldKey = document.getKey();
                    String file = fileUploadService.upload(FileUploadService.generateCustomerFileName(document.getCustomer().getId(), customerDocumentId, fileToUpload.getOriginalFilename()), fileToUpload.getInputStream().readAllBytes());
                    document.setKey(file);
                    document.setName(fileToUpload.getOriginalFilename());
                    repository.save(document);

                    if(!Strings.isBlank(oldKey)) {
                        fileUploadService.delete(oldKey);
                    }

                    activityService.create(document.getCustomer(), ActivityType.DOCUMENT_UPDATED, document.getType().getName());
                    return CustomerDocumentMapper.getInstance().entityToDTO(document);
                } catch (IOException e) {
                    throw new SISPJException(SISPJExceptionDefinition.DOCUMENT_UPLOAD_ERROR);
                }
            })
            .orElse(null);
    }

    public void deleteByCustomerId(Long customerId) {
        this.findByCustomerId(customerId).forEach(customerDocumentDTO ->
            this.delete(customerDocumentDTO.getId())
        );

        fileUploadService.delete(FileUploadService.generateCustomerDirectoryName(customerId));
    }
}