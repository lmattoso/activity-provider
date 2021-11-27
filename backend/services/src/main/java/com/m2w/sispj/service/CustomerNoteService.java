package com.m2w.sispj.service;

import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.customer.CustomerNote;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.dto.customer.CustomerNoteDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.CustomerNoteMapper;
import com.m2w.sispj.repository.CustomerNoteRepository;
import com.m2w.sispj.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerNoteService {

    private final CustomerNoteRepository repository;
    private final ActivityService activityService;
    private final CustomerRepository customerRepository;

    public CustomerNoteService(CustomerNoteRepository repository, ActivityService activityService, CustomerRepository customerRepository) {
        this.repository = repository;
        this.activityService = activityService;
        this.customerRepository = customerRepository;
    }

    public List<CustomerNoteDTO> findByCustomerId(Long customerId) {
        return repository.findByCustomerIdOrderByCreateDateDesc(customerId).stream()
            .map(CustomerNoteMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public CustomerNoteDTO create(CustomerNoteDTO customerNoteDTO) {
        Customer customer = customerRepository.findById(customerNoteDTO.getCustomerId())
            .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.CUSTOMER_NOT_EXISTS));

        CustomerNote customerNote = repository.save(CustomerNote.builder()
            .description(customerNoteDTO.getDescription())
            .customer(customer)
            .build());

        activityService.create(customerNote.getCustomer(), ActivityType.NOTE_CREATED, customerNote.getDescription());

        return CustomerNoteMapper.getInstance().entityToDTO(customerNote);
    }

    @Transactional
    public CustomerNoteDTO update(CustomerNoteDTO customerNoteDTO) {
        return repository.findById(customerNoteDTO.getId())
            .map(note -> {
                note.setDescription(customerNoteDTO.getDescription());
                repository.save(note);
                activityService.create(note.getCustomer(), ActivityType.NOTE_UPDATED, note.getDescription());
                return CustomerNoteMapper.getInstance().entityToDTO(note);
            })
            .orElse(null);
    }

    @Transactional
    public void delete(Long customerNoteId) {
        repository.findById(customerNoteId)
            .ifPresent(customerNote -> {
                repository.deleteById(customerNoteId);
                activityService.create(customerNote.getCustomer(), ActivityType.NOTE_DELETED, customerNote.getDescription());
            });
    }

    @Transactional
    public void deleteByCustomerId(Long customerId) {
        repository.deleteByCustomerId(customerId);
    }
}