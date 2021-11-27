package com.m2w.sispj.service;

import com.m2w.sispj.domain.Activity;
import com.m2w.sispj.domain.customer.Customer;
import com.m2w.sispj.domain.customer.CustomerProcess;
import com.m2w.sispj.domain.enums.ActivityType;
import com.m2w.sispj.dto.ActivityDTO;
import com.m2w.sispj.dto.customer.CustomerDTO;
import com.m2w.sispj.mapper.ActivityMapper;
import com.m2w.sispj.mapper.CustomerMapper;
import com.m2w.sispj.repository.ActivityRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository repository;

    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    public List<ActivityDTO> listByCustomerId(Long customerId, Pageable pageable) {
        return repository.findByCustomerIdOrderByCreateDateDesc(customerId, pageable).stream()
            .map(ActivityMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    public List<ActivityDTO> listByProcessId(Long customerId, Pageable pageable) {
        return repository.findByProcessIdOrderByCreateDateDesc(customerId, pageable).stream()
            .map(ActivityMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public CustomerDTO create(Customer customer, ActivityType type, String description) {
        return this.createActivity(customer, null, type, description);
    }

    @Transactional
    public CustomerDTO create(CustomerProcess process, ActivityType type, String description) {
        return this.createActivity(process.getCustomer(), process, type, description);
    }

    private CustomerDTO createActivity(Customer customer, CustomerProcess process, ActivityType type, String description) {
        Activity activity = Activity.builder()
            .customer(customer)
            .process(process)
            .description(description)
            .type(type)
            .build();

        repository.saveAndFlush(activity);

        return CustomerMapper.getInstance().entityToDTO(customer);
    }

    @Transactional
    public void delete(Long customerId) {
        List<Activity> activities = repository.findByCustomerIdOrderByCreateDateDesc(customerId, null);
        repository.deleteAll(activities);
    }

    @Transactional
    public void deleteByCustomerId(Long customerId) {
        repository.deleteByCustomerId(customerId);
    }
}