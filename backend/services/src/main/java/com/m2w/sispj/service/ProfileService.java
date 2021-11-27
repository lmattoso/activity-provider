package com.m2w.sispj.service;

import com.m2w.sispj.dto.ProfileDTO;
import com.m2w.sispj.mapper.ProfileMapper;
import com.m2w.sispj.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public List<ProfileDTO> list() {
        return repository.findAllByOrderByNameAsc().stream().map(ProfileMapper.getInstance()::entityToDTO).collect(Collectors.toList());
    }
}