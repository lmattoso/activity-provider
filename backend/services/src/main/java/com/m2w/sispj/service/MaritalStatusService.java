package com.m2w.sispj.service;

import com.m2w.sispj.domain.MaritalStatus;
import com.m2w.sispj.dto.SimpleEntityDTO;
import com.m2w.sispj.mapper.BaseEntityMapper;
import com.m2w.sispj.repository.MaritalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaritalStatusService {

    private MaritalStatusRepository repository;

    public MaritalStatusService(MaritalStatusRepository repository) {
        this.repository = repository;
    }

    public List<SimpleEntityDTO> list() {
        return repository.findAllByOrderByNameAsc().stream().map(BaseEntityMapper.getInstance()::entityToDTO).collect(Collectors.toList());
    }

    @Transactional
    public SimpleEntityDTO create(SimpleEntityDTO simpleEntityDTO) {
        MaritalStatus maritalStatus = MaritalStatus.builder().name(simpleEntityDTO.getName()).build();
        repository.save(maritalStatus);

        return BaseEntityMapper.getInstance().entityToDTO(maritalStatus);
    }
}