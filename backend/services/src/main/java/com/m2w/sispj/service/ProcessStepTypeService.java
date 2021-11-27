package com.m2w.sispj.service;

import com.m2w.sispj.domain.ProcessStepType;
import com.m2w.sispj.dto.ProcessStepTypeDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.ProcessStepTypeMapper;
import com.m2w.sispj.repository.ProcessStepTypeRepository;
import com.m2w.sispj.repository.ProcessTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProcessStepTypeService {

    private ProcessTypeRepository processTypeRepository;
    private ProcessStepTypeRepository repository;

    public ProcessStepTypeService(ProcessTypeRepository processTypeRepository, ProcessStepTypeRepository repository) {
        this.processTypeRepository = processTypeRepository;
        this.repository = repository;
    }

    public List<ProcessStepTypeDTO> list(Pageable pageable) {
        return repository.findAllByOrderByNameAsc(pageable).stream().map(ProcessStepTypeMapper.getInstance()::entityToDTO).collect(Collectors.toList());
    }

    public ProcessStepTypeDTO findById(Long stepTypeId) {
        return repository.findById(stepTypeId)
            .map(ProcessStepTypeMapper.getInstance()::entityToDTO)
            .orElseThrow(() -> new RuntimeException("Tipo de documento não existe!"));
    }

    @Transactional
    public ProcessStepTypeDTO create(ProcessStepTypeDTO stepTypeDTO) {
        this.validateCreate(stepTypeDTO);

        ProcessStepType stepType = repository.saveAndFlush(
            ProcessStepTypeMapper.getInstance().dtoToEntity(stepTypeDTO));

        return ProcessStepTypeMapper.getInstance().entityToDTO(stepType);
    }

    @Transactional
    public ProcessStepTypeDTO update(ProcessStepTypeDTO stepTypeDTO) {
        ProcessStepType stepType = this.validateUpdate(stepTypeDTO);

        this.updateProcessTypeAttributes(stepType, stepTypeDTO);
        repository.saveAndFlush(stepType);

        return ProcessStepTypeMapper.getInstance().entityToDTO(stepType);
    }

    private void validateCreate(ProcessStepTypeDTO stepTypeDTO) {
        if(!ObjectUtils.isEmpty(stepTypeDTO.getName()) && repository.existsByName(stepTypeDTO.getName())) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_STEP_TYPE_DUPLICATED_NAME);
        }
    }

    private ProcessStepType validateUpdate(ProcessStepTypeDTO stepTypeDTO) {
        Optional<ProcessStepType> stepType = repository.findById(stepTypeDTO.getId());

        if(stepType.isEmpty()) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_STEP_TYPE_NOT_EXISTS);
        }

        if(!ObjectUtils.isEmpty(stepTypeDTO.getName()) && repository.existsByNameAndIdNot(stepTypeDTO.getName(), stepTypeDTO.getId())) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_STEP_TYPE_DUPLICATED_NAME);
        }

        return stepType.get();
    }

    private void updateProcessTypeAttributes(ProcessStepType stepType, ProcessStepTypeDTO stepTypeDTO) {
        stepType.setName(stepTypeDTO.getName());
    }

    @Transactional
    public void delete(Long stepId) {
        boolean isUsed = !processTypeRepository.findByStepsId(stepId).isEmpty();

        if(isUsed) {
            throw new SISPJException(SISPJExceptionDefinition.PROCESS_STEP_TYPE_IS_USED);
        }

        repository.deleteById(stepId);
    }
}