package com.m2w.sispj.controller;

import com.m2w.sispj.dto.TemporaryLinkDTO;
import com.m2w.sispj.dto.customer.*;
import com.m2w.sispj.service.CustomerDocumentService;
import com.m2w.sispj.service.CustomerNoteService;
import com.m2w.sispj.service.CustomerProvisionService;
import com.m2w.sispj.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("customer")
@Slf4j
public class CustomerController {

    private final CustomerService service;
    private final CustomerNoteService customerNoteService;
    private final CustomerDocumentService customerDocumentService;
    private final CustomerProvisionService customerProvisionService;

    public CustomerController(CustomerService service, CustomerNoteService customerNoteService, CustomerDocumentService customerDocumentService, CustomerProvisionService customerProvisionService) {
        this.service = service;
        this.customerNoteService = customerNoteService;
        this.customerDocumentService = customerDocumentService;
        this.customerProvisionService = customerProvisionService;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerGridDTO>> list(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @GetMapping(value = "proceed")
    public void proceed() {
    }

    @GetMapping(value = "{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> readById(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(service.findById(customerId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO response = service.create(customerDTO);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO response = service.update(customerDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long customerId) {
        service.delete(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "add-services", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> addServices(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO response = service.addServices(customerDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "{customerId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerNoteDTO>> listComments(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(customerNoteService.findByCustomerId(customerId));
    }

    @PostMapping(value = "comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerNoteDTO> createNote(@RequestBody CustomerNoteDTO noteDTO) {
        return ResponseEntity.ok().body(customerNoteService.create(noteDTO));
    }

    @PutMapping(value = "comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerNoteDTO> updateNote(@RequestBody CustomerNoteDTO noteDTO) {
        return ResponseEntity.ok().body(customerNoteService.update(noteDTO));
    }

    @DeleteMapping(value = "comments/{customerNoteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNote(@PathVariable Long customerNoteId) {
        customerNoteService.delete(customerNoteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "{customerId}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDocumentDTO>> listDocuments(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(customerDocumentService.findByCustomerId(customerId));
    }

    @PostMapping(value = "documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDocumentDTO> addDocument(@RequestBody CustomerDocumentDTO documentDTO) {
        return ResponseEntity.ok().body(customerDocumentService.create(documentDTO));
    }

    @PostMapping("documents/{documentId}/upload")
    @ResponseStatus( HttpStatus.OK )
    public ResponseEntity<CustomerDocumentDTO> uploadDocument(@PathVariable final Long documentId, @RequestParam("file") MultipartFile files) {
        return ResponseEntity.ok().body(customerDocumentService.upload(files, documentId));
    }

    @PutMapping(value = "documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDocumentDTO> updateDocument(@RequestBody CustomerDocumentDTO documentDTO) {
        return ResponseEntity.ok().body(customerDocumentService.update(documentDTO));
    }

    @DeleteMapping(value = "documents/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDocument(@PathVariable Long documentId) {
        customerDocumentService.delete(documentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "documents/{documentId}/temporary-link", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TemporaryLinkDTO> getTemporaryLink(@PathVariable Long documentId) {
        return ResponseEntity.ok().body(customerDocumentService.getTemporaryLink(documentId));
    }

    @GetMapping(value = "{customerId}/provisions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProvisionResumeDTO> listProvisions(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(customerProvisionService.findCustomerProvisionResume(customerId));
    }

    @PostMapping(value = "provisions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProvisionDTO> createProvision(@RequestBody CustomerProvisionDTO noteDTO) {
        return ResponseEntity.ok().body(customerProvisionService.create(noteDTO));
    }

    @PutMapping(value = "provisions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProvisionDTO> updateProvision(@RequestBody CustomerProvisionDTO noteDTO) {
        return ResponseEntity.ok().body(customerProvisionService.update(noteDTO));
    }

    @DeleteMapping(value = "provisions/{customerProvisionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProvision(@PathVariable Long customerProvisionId) {
        customerProvisionService.delete(customerProvisionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}