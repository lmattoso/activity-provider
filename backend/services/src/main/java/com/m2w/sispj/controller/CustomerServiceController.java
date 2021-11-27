package com.m2w.sispj.controller;

import com.m2w.sispj.dto.ProcessTypeDTO;
import com.m2w.sispj.dto.customer.CustomerProcessDTO;
import com.m2w.sispj.dto.customer.CustomerProcessNoteDTO;
import com.m2w.sispj.service.CustomerProcessNoteService;
import com.m2w.sispj.service.CustomerProcessService;
import com.m2w.sispj.service.ProcessTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("service")
@Slf4j
public class CustomerServiceController {

    private final ProcessTypeService service;
    private final CustomerProcessService customerProcessService;
    private final CustomerProcessNoteService customerProcessNoteService;

    public CustomerServiceController(ProcessTypeService service, CustomerProcessService customerProcessService, CustomerProcessNoteService customerProcessNoteService) {
        this.service = service;
        this.customerProcessService = customerProcessService;
        this.customerProcessNoteService = customerProcessNoteService;
    }

    @GetMapping(value = "listTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessTypeDTO>> list(@SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessTypeDTO> create(@RequestBody ProcessTypeDTO processTypeDTO) {
        ProcessTypeDTO response = service.create(processTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "{processId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessDTO> readById(@PathVariable Long processId) {
        return ResponseEntity.ok().body(customerProcessService.findById(processId));
    }

    @PutMapping(value = "start", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessDTO> startService(@RequestBody CustomerProcessDTO customerProcessDTO) {
        return ResponseEntity.ok().body(customerProcessService.start(customerProcessDTO));
    }

    @PutMapping(value = "cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessDTO> cancelService(@RequestBody CustomerProcessDTO customerProcessDTO) {
        return ResponseEntity.ok().body(customerProcessService.cancel(customerProcessDTO));
    }

    @PutMapping(value = "proceed-step", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessDTO> proceedStep(@RequestBody CustomerProcessDTO customerProcessDTO) {
        return ResponseEntity.ok().body(customerProcessService.proceedStep(customerProcessDTO));
    }

    @PutMapping(value = "check-documents", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessDTO> checkDocuments(@RequestBody CustomerProcessDTO customerProcessDTO) {
        return ResponseEntity.ok().body(customerProcessDTO);
    }

    @GetMapping(value = "{customerId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerProcessNoteDTO>> listComments(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(customerProcessNoteService.findByCustomerId(customerId));
    }

    @PostMapping(value = "comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessNoteDTO> createNote(@RequestBody CustomerProcessNoteDTO noteDTO) {
        return ResponseEntity.ok().body(customerProcessNoteService.create(noteDTO));
    }

    @PutMapping(value = "comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProcessNoteDTO> updateNote(@RequestBody CustomerProcessNoteDTO noteDTO) {
        return ResponseEntity.ok().body(customerProcessNoteService.update(noteDTO));
    }

    @DeleteMapping(value = "comments/{customerNoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNote(@PathVariable Long customerNoteId) {
        customerProcessNoteService.delete(customerNoteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}