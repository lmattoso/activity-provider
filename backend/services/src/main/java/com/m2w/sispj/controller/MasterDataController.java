package com.m2w.sispj.controller;

import com.m2w.sispj.dto.SimpleEntityDTO;
import com.m2w.sispj.service.CountryService;
import com.m2w.sispj.service.MaritalStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("master-data")
@Slf4j
public class MasterDataController {

    private MaritalStatusService maritalStatusService;
    private CountryService countryService;

    public MasterDataController(MaritalStatusService maritalStatusService, CountryService countryService) {
        this.maritalStatusService = maritalStatusService;
        this.countryService = countryService;
    }

    @GetMapping(value = "marital-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimpleEntityDTO>> listMaritalStatus() {
        return ResponseEntity.ok().body(maritalStatusService.list());
    }

    @GetMapping(value = "country", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimpleEntityDTO>> listCountry() {
        return ResponseEntity.ok().body(countryService.list());
    }
}