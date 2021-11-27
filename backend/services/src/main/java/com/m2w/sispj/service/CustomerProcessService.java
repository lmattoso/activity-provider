package com.m2w.sispj.service;

import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.customer.CustomerProcess;
import com.m2w.sispj.domain.customer.CustomerProcessDocument;
import com.m2w.sispj.domain.customer.CustomerProcessStep;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.domain.enums.ProcessStatus;
import com.m2w.sispj.domain.enums.ProvisionType;
import com.m2w.sispj.dto.ProcessTypeDTO;
import com.m2w.sispj.dto.customer.CustomerProcessDTO;
import com.m2w.sispj.dto.customer.CustomerProcessDocumentDTO;
import com.m2w.sispj.dto.customer.CustomerProvisionDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.CustomerProcessMapper;
import com.m2w.sispj.mapper.ProcessMapper;
import com.m2w.sispj.mapper.ProcessTypeMapper;
import com.m2w.sispj.repository.CustomerProcessDocumentRepository;
import com.m2w.sispj.repository.CustomerProcessNoteRepository;
import com.m2w.sispj.repository.CustomerProcessRepository;
import com.m2w.sispj.repository.CustomerProcessStepRepository;
import com.m2w.sispj.util.SISPJUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CustomerProcessService {

    private final CustomerProcessRepository repository;
    private final CustomerProcessStepRepository stepRepository;
    private final CustomerProcessDocumentRepository documentRepository;
    private final CustomerProcessNoteRepository customerProcessNoteRepository;
    private final CustomerProvisionService customerProvisionService;
    private final ActivityService activityService;

    public CustomerProcessService(CustomerProcessRepository repository,
                                  CustomerProcessStepRepository stepRepository,
                                  CustomerProcessDocumentRepository documentRepository,
                                  CustomerProcessNoteRepository customerProcessNoteRepository,
                                  CustomerProvisionService customerProvisionService,
                                  ActivityService activityService)
    {
        this.repository = repository;
        this.stepRepository = stepRepository;
        this.documentRepository = documentRepository;
        this.customerProcessNoteRepository = customerProcessNoteRepository;
        this.customerProvisionService = customerProvisionService;
        this.activityService = activityService;
    }

    public CustomerProcessDTO findById(Long processId) {
        Optional<CustomerProcess> service = repository.findById(processId);

        if(service.isPresent()) {
            CustomerProcessDTO process = ProcessMapper.getInstance().entityToDTO(service.get());
            process.setSteps(CustomerProcessMapper.getInstance().stepEntitiesToDTO(stepRepository.findByProcessIdOrderByOrder(processId)));
            process.setDocuments(CustomerProcessMapper.getInstance().documentEntitiesToDTO(documentRepository.findByProcessIdOrderByTypeName(processId)));
            return process;
        }

        throw new RuntimeException("Serviço não existe!");
    }

    private CustomerProcess findValidCustomer(Long customerId) {
        Optional<CustomerProcess> process = repository.findById(customerId);

        if(process.isEmpty()) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_NOT_EXISTS);
        }

        return process.get();
    }

    @Transactional
    public void create(Customer customer, List<ProcessTypeDTO> processTypeDTOS) {

        if(processTypeDTOS != null) {
            processTypeDTOS.forEach(serviceTypeDTO -> {
                CustomerProcess process = CustomerProcess.builder()
                    .customer(customer)
                    .type(ProcessTypeMapper.getInstance().dtoToEntity(serviceTypeDTO))
                    .status(ProcessStatus.NOT_STARTED)
                    .build();

                repository.saveAndFlush(process);

                activityService.create(process, ActivityType.SERVICE_CREATED, process.getType().getName());

                customerProvisionService.create(
                    CustomerProvisionDTO.builder()
                        .type(ProvisionType.PROVISION)
                        .customerId(customer.getId())
                        .description(process.getType().getName())
                        .serviceValue(process.getType().getPrice())
                        .build(),
                    process
                );
            });
        }
    }

    @Transactional
    public CustomerProcessDTO start(CustomerProcessDTO customerProcessDTO) {

        CustomerProcess process = this.findValidCustomer(customerProcessDTO.getId());

        if(!ProcessStatus.NOT_STARTED.equals(process.getStatus())) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_ALREADY_STARTED);
        }

        process.setStartDate(SISPJUtil.today());
        process.setStatus(ProcessStatus.IN_PROGRESS);
        process.setStartDate(SISPJUtil.today());
        repository.saveAndFlush(process);

        AtomicInteger order = new AtomicInteger(0);
        process.getType().getSteps().forEach(processStepType -> {
            CustomerProcessStep step = CustomerProcessStep.builder()
                .process(process)
                .type(processStepType)
                .status(ProcessStatus.NOT_STARTED)
                .order(order.incrementAndGet())
                .build();

            if(step.getOrder() == 1) {
                step.setStartDate(SISPJUtil.today());
            }

            stepRepository.saveAndFlush(step);
        });

        process.getType().getDocuments().forEach(documentType -> {
            CustomerProcessDocument document = CustomerProcessDocument.builder()
                .process(process)
                .type(documentType)
                .status(ProcessStatus.NOT_STARTED)
                .build();

            documentRepository.saveAndFlush(document);
        });

        activityService.create(process, ActivityType.SERVICE_STARTED, process.getType().getName());

        return this.findById(customerProcessDTO.getId());
    }

    @Transactional
    public CustomerProcessDTO cancel(CustomerProcessDTO customerProcessDTO) {
        CustomerProcess process = this.findValidCustomer(customerProcessDTO.getId());

        if(ProcessStatus.FINISHED.equals(process.getStatus())) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_ALREADY_FINISHED);
        }

        process.setCancellationReason(customerProcessDTO.getCancellationReason());
        process.setStatus(ProcessStatus.CANCELLED);
        repository.saveAndFlush(process);

        activityService.create(process, ActivityType.SERVICE_CANCELLED, String.format("%s - Motivo: %s", process.getType().getName(), process.getCancellationReason()));

        return this.findById(customerProcessDTO.getId());
    }

    @Transactional
    public CustomerProcessDTO proceedStep(CustomerProcessDTO customerProcessDTO) {

        CustomerProcess process = this.findValidCustomer(customerProcessDTO.getId());

        if(ProcessStatus.FINISHED.equals(process.getStatus())) {
            process.setArchivePath(customerProcessDTO.getArchivePath());
            repository.saveAndFlush(process);
        } else {
            List<CustomerProcessStep> steps = stepRepository.findByProcessIdOrderByOrder(customerProcessDTO.getId());

            CustomerProcessStep currentStep = this.findCurrentStep(steps);
            CustomerProcessStep nextStep = this.findNextStep(steps);
            AtomicBoolean stepFinished = new AtomicBoolean(true);

            if(currentStep != null) {
                if(currentStep.getType().getId() == 1) {
                    List<CustomerProcessDocument> documents = documentRepository.findByProcessIdOrderByTypeName(customerProcessDTO.getId());

                    List<Long> documentIds = customerProcessDTO.getDocuments().stream()
                        .map(CustomerProcessDocumentDTO::getId)
                        .collect(Collectors.toList());

                    documents.stream()
                        .filter(customerProcessDocument -> Objects.isNull(customerProcessDocument.getCheckDate()))
                        .forEach(customerProcessDocument -> {
                            if(documentIds.contains(customerProcessDocument.getId())) {
                                customerProcessDocument.setCheckDate(SISPJUtil.today());
                                activityService.create(process, ActivityType.SERVICE_DOCUMENT_VERIFIED, customerProcessDocument.getType().getName());
                            } else {
                                customerProcessDocument.setCheckDate(null);
                                stepFinished.set(false);
                            }

                            documentRepository.saveAndFlush(customerProcessDocument);
                        });
                } else if(currentStep.getType().getId() == 5) {
                    process.setArchivePath(customerProcessDTO.getArchivePath());
                    currentStep.setEndDate(SISPJUtil.today());
                }

                if(stepFinished.get()) {
                    currentStep.setEndDate(SISPJUtil.today());
                    activityService.create(process, ActivityType.SERVICE_STEP_COMPLETED, currentStep.getType().getName());

                    if(nextStep != null) {
                        nextStep.setStartDate(SISPJUtil.today());
                    } else {
                        process.setEndDate(SISPJUtil.today());
                        process.setStatus(ProcessStatus.FINISHED);
                        activityService.create(process, ActivityType.SERVICE_FINISHED, process.getType().getName());
                    }
                }

                stepRepository.saveAndFlush(currentStep);
            }
        }

        return this.findById(customerProcessDTO.getId());
    }

    private CustomerProcessStep findCurrentStep(List<CustomerProcessStep> steps) {
        for(CustomerProcessStep step : steps) {
            if(step.getStartDate() != null && step.getEndDate() == null) {
                return step;
            }
        }
        return null;
    }

    private CustomerProcessStep findNextStep(List<CustomerProcessStep> steps) {
        boolean currentStepFound = false;
        for(CustomerProcessStep step : steps) {
            if(currentStepFound) {
                return step;
            }

            if(step.getStartDate() != null && step.getEndDate() == null) {
                currentStepFound = true;
            }
        }
        return null;
    }

    @Transactional
    public void delete(Long customerId) {
        stepRepository.deleteByProcessCustomerId(customerId);
        documentRepository.deleteByProcessCustomerId(customerId);
        customerProcessNoteRepository.deleteByProcessCustomerId(customerId);
        repository.deleteByCustomerId(customerId);
    }
}