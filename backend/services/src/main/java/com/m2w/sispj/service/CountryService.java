package com.m2w.sispj.service;

import com.m2w.sispj.dto.SimpleEntityDTO;
import com.m2w.sispj.mapper.BaseEntityMapper;
import com.m2w.sispj.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<SimpleEntityDTO> list() {
        return repository.findAllByOrderByNameAsc().stream()
            .map(BaseEntityMapper.getInstance()::entityToDTO)
            .collect(Collectors.toList());
    }
}