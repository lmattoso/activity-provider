package com.m2w.sispj.service;

import com.m2w.sispj.domain.Country;
import com.m2w.sispj.domain.MaritalStatus;
import com.m2w.sispj.domain.User;
import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.dto.customer.*;
import com.m2w.sispj.dto.customer.CustomerDTO;
import com.m2w.sispj.dto.customer.CustomerGridDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.CustomerMapper;
import com.m2w.sispj.mapper.ProcessMapper;
import com.m2w.sispj.mapper.ProcessTypeMapper;
import com.m2w.sispj.repository.CustomerRepository;
import com.m2w.sispj.util.SISPJUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerProcessService customerProcessService;
    private final CustomerNoteService customerNoteService;
    private final CustomerProvisionService customerProvisionService;
    private final CustomerDocumentService customerDocumentService;
    private final ActivityService activityService;

    public CustomerService(CustomerRepository repository, CustomerProcessService customerProcessService, CustomerNoteService customerNoteService, CustomerDocumentService customerDocumentService, ActivityService activityService, CustomerProvisionService customerProvisionService) {
        this.repository = repository;
        this.customerProcessService = customerProcessService;
        this.customerNoteService = customerNoteService;
        this.customerDocumentService = customerDocumentService;
        this.activityService = activityService;
        this.customerProvisionService = customerProvisionService;
    }

    public List<CustomerGridDTO> list(Pageable pageable) {
        return repository.findAll(pageable).stream().map(CustomerMapper.getInstance()::entityToGridDTO).collect(Collectors.toList());
    }

    public CustomerDTO findById(Long customerId) {
        Customer customer = this.findValidCustomer(customerId);

        CustomerDTO customerDTO = CustomerMapper.getInstance().entityToDTO(customer);

        customerDTO.setServices(customer.getCustomerProcesses().stream().map(service ->
            ProcessTypeMapper.getInstance().entityToDTO(service.getType())
        ).collect(Collectors.toList()));

        customerDTO.setServicesCustomer(customer.getCustomerProcesses().stream().map(
            ProcessMapper.getInstance()::entityToDTO
        ).sorted(Comparator.comparingInt((CustomerProcessDTO o) -> o.getStatus().getOrder()).thenComparing(o -> o.getType().getName()))
        .collect(Collectors.toList()));

        return customerDTO;
    }

    @Transactional
    public CustomerDTO create(CustomerDTO customerDTO) {
        validateNewCustomer(customerDTO);

        Customer customer = repository.saveAndFlush(CustomerMapper.getInstance().dtoToEntity(customerDTO));

        activityService.create(customer, ActivityType.CUSTOMER_CREATED, customer.getName());

        customerProcessService.create(customer, customerDTO.getServices());

        if(!ObjectUtils.isEmpty(customer.getObservation())) {
            customerNoteService.create(CustomerNoteDTO.builder()
                .customerId(customer.getId())
                .description(customer.getObservation())
                .build());
        }

        return CustomerMapper.getInstance().entityToDTO(customer);
    }

    private Customer findValidCustomer(Long customerId) {
        return repository.findById(customerId)
            .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.CUSTOMER_NOT_EXISTS));
    }

    @Transactional
    public CustomerDTO addServices(CustomerDTO customerDTO) {
        Customer customer = this.findValidCustomer(customerDTO.getId());
        customerProcessService.create(customer, customerDTO.getServices());
        return this.findById(customerDTO.getId());
    }

    @Transactional
    public CustomerDTO update(CustomerDTO customerDTO) {
        Customer customer = this.findValidCustomer(customerDTO.getId());

        this.validateUpdateCustomer(customerDTO);

        this.updateCustomerAttributes(customer, customerDTO);
        repository.saveAndFlush(customer);

        activityService.create(customer, ActivityType.CUSTOMER_UPDATED, customer.getName());

        return CustomerMapper.getInstance().entityToDTO(customer);
    }

    private void validateUpdateCustomer(CustomerDTO customerDTO) {
        if(!ObjectUtils.isEmpty(customerDTO.getEmail()) && repository.existsByEmailAndIdNot(customerDTO.getEmail(), customerDTO.getId())) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DUPLICATED_EMAIL);
        }

        if(!ObjectUtils.isEmpty(customerDTO.getNif()) && repository.existsByNifAndIdNot(customerDTO.getNif(), customerDTO.getId())) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DUPLICATED_NIF);
        }

        if(!ObjectUtils.isEmpty(customerDTO.getNiss()) && repository.existsByNissAndIdNot(customerDTO.getNiss(), customerDTO.getId())) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DUPLICATED_NISS);
        }
    }

    private void updateCustomerAttributes(Customer customer, CustomerDTO customerDTO) {
        customer.setName(customerDTO.getName());
        customer.setGenre(customerDTO.getGenre());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setAddressNumber(customerDTO.getAddressNumber());
        customer.setPostalCode(customerDTO.getPostalCode());
        customer.setLocality(customerDTO.getLocality());
        customer.setAddressFloor(customerDTO.getAddressFloor());
        customer.setMaritalStatus(MaritalStatus.builder().id(customerDTO.getMaritalStatusId()).build());
        customer.setCountry(Country.builder().id(customerDTO.getCountryId()).build());
        customer.setProfession(customerDTO.getProfession());
        customer.setQualification(customerDTO.getQualification());
        customer.setNif(customerDTO.getNif());
        customer.setNifPassword(customerDTO.getNifPassword());
        customer.setNiss(customerDTO.getNiss());
        customer.setNissPassword(customerDTO.getNissPassword());
        customer.setEmailSapa(customerDTO.getEmailSapa());
        customer.setEmailSapaPassword(customerDTO.getEmailSapaPassword());
        customer.setEmailSef(customerDTO.getEmailSef());
        customer.setEmailSefPassword(customerDTO.getEmailSefPassword());
        customer.setEmail(customerDTO.getEmail());
        customer.setWhattsapp(customerDTO.getWhattsapp());
        customer.setFatherName(customerDTO.getFatherName());
        customer.setMotherName(customerDTO.getMotherName());
        customer.setObservation(customerDTO.getObservation());
        customer.setParish(customerDTO.getParish());
        customer.setCounty(customerDTO.getCounty());
        customer.setNumber1(customerDTO.getNumber1());
        customer.setPassword1(customerDTO.getPassword1());
        customer.setObservation1(customerDTO.getObservation1());
        customer.setNumber2(customerDTO.getNumber2());
        customer.setPassword2(customerDTO.getPassword2());
        customer.setObservation2(customerDTO.getObservation2());
    }

    @Transactional
    public void delete(Long customerId) {
        User currentUser = SISPJUtil.getCurrentUser();

        if(!currentUser.isAdmin()) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DELETE);
        }

        customerDocumentService.deleteByCustomerId(customerId);
        customerNoteService.deleteByCustomerId(customerId);
        customerProcessService.delete(customerId);
        customerProvisionService.deleteByCustomerId(customerId);
        activityService.deleteByCustomerId(customerId);
        repository.deleteById(customerId);
    }

    private void validateNewCustomer(CustomerDTO customerDTO) {
        if(!ObjectUtils.isEmpty(customerDTO.getEmail()) && repository.existsByEmail(customerDTO.getEmail())) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DUPLICATED_EMAIL);
        }

        if(!ObjectUtils.isEmpty(customerDTO.getNif()) && repository.existsByNif(customerDTO.getNif())) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DUPLICATED_NIF);
        }

        if(!ObjectUtils.isEmpty(customerDTO.getNiss()) && repository.existsByNiss(customerDTO.getNiss())) {
            throw new SISPJException(SISPJExceptionDefinition.CUSTOMER_DUPLICATED_NISS);
        }
    }
}