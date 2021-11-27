package com.m2w.sispj.controller;

import com.m2w.sispj.dto.ProcessStepTypeDTO;
import com.m2w.sispj.service.ProcessStepTypeService;
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
@RequestMapping("step-type")
@Slf4j
public class ProcessStepTypeController {

    private ProcessStepTypeService service;

    public ProcessStepTypeController(ProcessStepTypeService service) {
        this.service = service;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessStepTypeDTO>> listDocumentTypes(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @GetMapping(value = "{stepTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessStepTypeDTO> readById(@PathVariable Long stepTypeId) {
        return ResponseEntity.ok().body(service.findById(stepTypeId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessStepTypeDTO> create(@RequestBody ProcessStepTypeDTO processTypeDTO) {
        ProcessStepTypeDTO response = service.create(processTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessStepTypeDTO> update(@RequestBody ProcessStepTypeDTO documentTypeDTO) {
        ProcessStepTypeDTO response = service.update(documentTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "{stepTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Long stepTypeId) {
        service.delete(stepTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}