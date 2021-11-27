package com.m2w.sispj.service;

import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.customer.CustomerProcess;
import com.m2w.sispj.domain.customer.CustomerProvision;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.domain.enums.ProvisionType;
import com.m2w.sispj.dto.customer.CustomerProvisionDTO;
import com.m2w.sispj.dto.customer.CustomerProvisionResumeDTO;
import com.m2w.sispj.error.exception.SISPJException;
import com.m2w.sispj.error.exception.SISPJExceptionDefinition;
import com.m2w.sispj.mapper.CustomerProvisionMapper;
import com.m2w.sispj.repository.CustomerProvisionRepository;
import com.m2w.sispj.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerProvisionService {

    private final CustomerProvisionRepository repository;
    private final ActivityService activityService;
    private final CustomerRepository customerRepository;

    public CustomerProvisionService(CustomerProvisionRepository repository, ActivityService activityService, CustomerRepository customerRepository) {
        this.repository = repository;
        this.activityService = activityService;
        this.customerRepository = customerRepository;
    }

    public CustomerProvisionResumeDTO findCustomerProvisionResume(Long customerId) {
        List<CustomerProvisionDTO> provisions = this.findByCustomerId(customerId);

        BigDecimal total = provisions.stream()
            .map(CustomerProvisionDTO::getServiceValue)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPayed = provisions.stream()
            .map(CustomerProvisionDTO::getPaymentValue)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CustomerProvisionResumeDTO.builder()
            .provisions(provisions)
            .total(total)
            .totalPayed(totalPayed)
            .totalDue(total.subtract(totalPayed))
            .build();
    }

    public List<CustomerProvisionDTO> findByCustomerId(Long customerId) {
        return repository.findByCustomerIdOrderByCreateDate(customerId).stream()
            .map(CustomerProvisionMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public CustomerProvisionDTO create(CustomerProvisionDTO customerProvisionDTO) {
        return this.create(customerProvisionDTO, null);
    }

    @Transactional
    public CustomerProvisionDTO create(CustomerProvisionDTO customerProvisionDTO, CustomerProcess customerProcess) {
        Customer customer = customerRepository.findById(customerProvisionDTO.getCustomerId())
            .orElseThrow(() -> new SISPJException(SISPJExceptionDefinition.CUSTOMER_NOT_EXISTS));

        CustomerProvision provision = CustomerProvisionMapper.getInstance().dtoToEntity(customerProvisionDTO);
        provision.setCustomer(customer);
        provision.setProcess(customerProcess);

        CustomerProvision customerProvision = repository.save(provision);

        if(ProvisionType.PROVISION.equals(customerProvision.getType())) {
            activityService.create(customerProvision.getCustomer(), ActivityType.PROVISION_CREATED, customerProvision.getFormattedDescription());
        } else {
            activityService.create(customerProvision.getCustomer(), ActivityType.PAYMENT_CREATED, customerProvision.getFormattedPayment());
        }

        return CustomerProvisionMapper.getInstance().entityToDTO(customerProvision);
    }

    @Transactional
    public CustomerProvisionDTO update(CustomerProvisionDTO provisionDTO) {
        return repository.findById(provisionDTO.getId())
            .map(customerProvision -> {
                CustomerProvision newProvision = CustomerProvisionMapper.getInstance().dtoToEntity(provisionDTO);
                customerProvision.setDescription(newProvision.getDescription());
                customerProvision.setServiceValue(newProvision.getServiceValue());
                customerProvision.setPaymentValue(newProvision.getPaymentValue());
                customerProvision.setPaymentDate(newProvision.getPaymentDate());
                repository.save(customerProvision);

                if(ProvisionType.PROVISION.equals(customerProvision.getType())) {
                    activityService.create(customerProvision.getCustomer(), ActivityType.PROVISION_UPDATED, customerProvision.getFormattedDescription());
                } else {
                    activityService.create(customerProvision.getCustomer(), ActivityType.PAYMENT_UPDATED, customerProvision.getFormattedPayment());
                }

                return CustomerProvisionMapper.getInstance().entityToDTO(customerProvision);
            })
            .orElse(null);
    }

    public void delete(Long customerProvisionId) {
        repository.findById(customerProvisionId)
            .ifPresent(customerProvision -> {
                repository.deleteById(customerProvisionId);

                if(ProvisionType.PROVISION.equals(customerProvision.getType())) {
                    activityService.create(customerProvision.getCustomer(), ActivityType.PROVISION_DELETED, customerProvision.getFormattedDescription());
                } else {
                    activityService.create(customerProvision.getCustomer(), ActivityType.PAYMENT_DELETED, customerProvision.getFormattedPayment());
                }
            });
    }

    public void deleteByCustomerId(Long customerId) {
        repository.deleteByCustomerId(customerId);
    }
}