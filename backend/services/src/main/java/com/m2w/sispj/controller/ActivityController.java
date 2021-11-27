package com.m2w.sispj.controller;

import com.m2w.sispj.dto.ActivityDTO;
import com.m2w.sispj.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("activity")
@Slf4j
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @GetMapping(value = "customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityDTO>> readByCustomerId(@PathVariable Long customerId, @SortDefault(sort = "createDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.listByCustomerId(customerId, pageable));
    }

    @GetMapping(value = "service/{serviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityDTO>> readByProcessId(@PathVariable Long serviceId, @SortDefault(sort = "createDate", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.listByProcessId(serviceId, pageable));
    }
}