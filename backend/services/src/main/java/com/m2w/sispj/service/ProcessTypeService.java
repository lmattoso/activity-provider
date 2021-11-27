package com.m2w.sispj.service;

import com.m2w.sispj.domain.ProcessType;
import com.m2w.sispj.domain.User;
import com.m2w.sispj.dto.ProcessTypeDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.DocumentTypeMapper;
import com.m2w.sispj.mapper.ProcessStepTypeMapper;
import com.m2w.sispj.mapper.ProcessTypeMapper;
import com.m2w.sispj.repository.CustomerProcessRepository;
import com.m2w.sispj.repository.ProcessTypeRepository;
import com.m2w.sispj.util.SISPJUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessTypeService {

    private final ProcessTypeRepository repository;
    private final CustomerProcessRepository customerProcessRepository;

    public ProcessTypeService(ProcessTypeRepository repository, CustomerProcessRepository customerProcessRepository) {
        this.repository = repository;
        this.customerProcessRepository = customerProcessRepository;
    }

    public List<ProcessTypeDTO> list(Pageable pageable) {
        return repository.findAllByOrderByNameAscVersionNumberAsc(pageable).stream()
            .map(ProcessTypeMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    public List<ProcessTypeDTO> listPublished(Pageable pageable) {
        return repository.findAllByPublishDateNotNullAndDeletedFalse(pageable).stream().map(ProcessTypeMapper.getInstance()::entityToDTO).collect(Collectors.toList());
    }

    public ProcessTypeDTO findById(Long processTypeId) {
        Optional<ProcessType> processType = repository.findById(processTypeId);

        if(processType.isPresent()) {
            return ProcessTypeMapper.getInstance().entityToDTO(processType.get());
        }

        throw new RuntimeException("Processo não existe!");
    }

    @Transactional
    public ProcessTypeDTO create(ProcessTypeDTO processTypeDTO) {
        this.validateCreate(processTypeDTO);

        ProcessType processType = ProcessTypeMapper.getInstance().dtoToEntity(processTypeDTO);
        processType.setPublishDate(null);
        processType.setVersionNumber(createVersionNumber(repository.findByNameAndPublishDateNotNull(processType.getName()).size() + 1));
        repository.saveAndFlush(processType);

        return ProcessTypeMapper.getInstance().entityToDTO(processType);
    }

    private String createVersionNumber(int quantity) {
        return String.format("V-%05d", quantity);
    }

    @Transactional
    public ProcessTypeDTO update(ProcessTypeDTO processTypeDTO) {
        ProcessType processType = this.validateUpdate(processTypeDTO);

        boolean isUsed = !customerProcessRepository.findByTypeId(processType.getId()).isEmpty();

        if(!isUsed) {
            this.updateProcessAttributes(processType, processTypeDTO);
            repository.saveAndFlush(processType);
            return ProcessTypeMapper.getInstance().entityToDTO(processType);
        } else {
            processType.setDeleted(true);
            repository.saveAndFlush(processType);
            processTypeDTO.setId(null);
            return this.create(processTypeDTO);
        }
    }

    @Transactional
    public ProcessTypeDTO publish(Long id) {
        ProcessType processType = repository.findById(id)
            .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.PROCESS_TYPE_NOT_EXISTS));

        User user = SISPJUtil.getCurrentUser();
        
        closePreviousVersion(processType);

        processType.setPublishBy(user);
        processType.setPublishDate(LocalDateTime.now());
        repository.saveAndFlush(processType);

        return ProcessTypeMapper.getInstance().entityToDTO(processType);
    }

    private void closePreviousVersion(ProcessType processType) {
        List<ProcessType> processes = repository.findByNameAndPublishDateNotNull(processType.getName());

        processes.forEach(process -> {
            process.setDeleted(true);
            repository.saveAndFlush(process);
        });
    }

    @Transactional
    public ProcessTypeDTO createNewVersion(Long id) {
        ProcessTypeDTO processTypeDTO = this.findById(id);

        if(!ObjectUtils.isEmpty(processTypeDTO.getName()) && repository.existsByNameAndIdNotAndDeletedAndPublishDateNull(processTypeDTO.getName(), processTypeDTO.getId(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_TYPE_DUPLICATED_NAME_EDITION);
        }

        ProcessType processType = ProcessType.builder()
            .name(processTypeDTO.getName())
            .price(processTypeDTO.getPrice())
            .versionNumber(this.createVersionNumber(repository.findByNameAndPublishDateNotNull(processTypeDTO.getName()).size() + 1))
            .documents(DocumentTypeMapper.getInstance().dtoToEntities(processTypeDTO.getDocuments()))
            .steps(ProcessStepTypeMapper.getInstance().dtoToEntities(processTypeDTO.getSteps()))
            .build();

        repository.saveAndFlush(processType);

        return ProcessTypeMapper.getInstance().entityToDTO(processType);
    }

    private void validateCreate(ProcessTypeDTO processTypeDTO) {
        if(!ObjectUtils.isEmpty(processTypeDTO.getName()) && repository.existsByNameAndDeleted(processTypeDTO.getName(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_TYPE_DUPLICATED_NAME);
        }
    }

    private ProcessType validateUpdate(ProcessTypeDTO processTypeDTO) {
        Optional<ProcessType> processType = repository.findById(processTypeDTO.getId());

        if(processType.isEmpty()) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_TYPE_NOT_EXISTS);
        }

        if(!ObjectUtils.isEmpty(processTypeDTO.getName()) && repository.existsByNameAndIdNotAndDeletedAndPublishDateNull(processTypeDTO.getName(), processTypeDTO.getId(), false)) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_TYPE_DUPLICATED_NAME);
        }

        return processType.get();
    }

    private void updateProcessAttributes(ProcessType processType, ProcessTypeDTO processTypeDTO) {
        processType.setName(processTypeDTO.getName());
        processType.setPrice(processTypeDTO.getPrice());
        processType.setSteps(ProcessStepTypeMapper.getInstance().dtoToEntities(processTypeDTO.getSteps()));
        processType.setDocuments(DocumentTypeMapper.getInstance().dtoToEntities(processTypeDTO.getDocuments()));
    }

    @Transactional
    public void delete(Long id) {
        boolean isUsed = !customerProcessRepository.findByTypeId(id).isEmpty();

        if(isUsed) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_TYPE_IS_USED);
        }

        repository.deleteById(id);
    }
}