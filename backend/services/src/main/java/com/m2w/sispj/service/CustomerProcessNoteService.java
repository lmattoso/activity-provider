package com.m2w.sispj.service;

import com.m2w.sispj.domain.customer.CustomerProcess;
import com.m2w.sispj.domain.customer.CustomerProcessNote;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.dto.customer.CustomerProcessNoteDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.CustomerProcessNoteMapper;
import com.m2w.sispj.repository.CustomerProcessNoteRepository;
import com.m2w.sispj.repository.CustomerProcessRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerProcessNoteService {

    private final CustomerProcessNoteRepository repository;
    private final CustomerProcessRepository customerRepository;
    private final ActivityService activityService;

    public CustomerProcessNoteService(CustomerProcessNoteRepository repository, CustomerProcessRepository customerRepository, ActivityService activityService) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.activityService = activityService;
    }

    public List<CustomerProcessNoteDTO> findByCustomerId(Long processId) {
        return repository.findByProcessIdOrderByCreateDateDesc(processId).stream()
            .map(CustomerProcessNoteMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public CustomerProcessNoteDTO create(CustomerProcessNoteDTO customerProcessNoteDTO) {
        CustomerProcess process = customerRepository.findById(customerProcessNoteDTO.getProcessId())
                .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.PROCESS_NOT_EXISTS));

        CustomerProcessNote customerNote = repository.save(CustomerProcessNote.builder()
                .description(customerProcessNoteDTO.getDescription())
                .process(process)
                .build());

        activityService.create(customerNote.getProcess(), ActivityType.NOTE_CREATED, customerNote.getDescription());

        return CustomerProcessNoteMapper.getInstance().entityToDTO(customerNote);
    }

    @Transactional
    public CustomerProcessNoteDTO update(CustomerProcessNoteDTO customerProcessNoteDTO) {
        return repository.findById(customerProcessNoteDTO.getId())
            .map(note -> {
                note.setDescription(customerProcessNoteDTO.getDescription());
                repository.save(note);
                activityService.create(note.getProcess(), ActivityType.NOTE_UPDATED, note.getDescription());
                return CustomerProcessNoteMapper.getInstance().entityToDTO(note);
            })
            .orElse(null);
    }

    public void delete(Long customerProcessNoteId) {
        repository.findById(customerProcessNoteId)
            .ifPresent(customerNote -> {
                repository.deleteById(customerProcessNoteId);
                activityService.create(customerNote.getProcess(), ActivityType.NOTE_DELETED, customerNote.getDescription());
            });
    }
}