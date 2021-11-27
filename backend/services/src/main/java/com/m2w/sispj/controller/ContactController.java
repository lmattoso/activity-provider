package com.m2w.sispj.controller;

import com.m2w.sispj.dto.ContactDTO;
import com.m2w.sispj.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contact")
@Slf4j
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContactDTO>> list(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @GetMapping(value = "{processTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactDTO> readById(@PathVariable Long processTypeId) {
        return ResponseEntity.ok().body(service.findById(processTypeId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactDTO> create(@RequestBody ContactDTO contactDTO) {
        ContactDTO response = service.create(contactDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactDTO> update(@RequestBody ContactDTO contactDTO) {
        ContactDTO response = service.update(contactDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "{processTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Long processTypeId) {
        service.delete(processTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}