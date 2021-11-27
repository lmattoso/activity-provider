package com.m2w.sispj.controller;

import com.m2w.sispj.dto.ProcessTypeDTO;
import com.m2w.sispj.service.ProcessTypeService;
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
@RequestMapping("process-type")
@Slf4j
public class ProcessTypeController {

    private ProcessTypeService service;

    public ProcessTypeController(ProcessTypeService service) {
        this.service = service;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessTypeDTO>> list(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @GetMapping(value = "list-published", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProcessTypeDTO>> listPublished(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.listPublished(pageable));
    }

    @GetMapping(value = "{processTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessTypeDTO> readById(@PathVariable Long processTypeId) {
        return ResponseEntity.ok().body(service.findById(processTypeId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessTypeDTO> create(@RequestBody ProcessTypeDTO processTypeDTO) {
        ProcessTypeDTO response = service.create(processTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessTypeDTO> update(@RequestBody ProcessTypeDTO processTypeDTO) {
        ProcessTypeDTO response = service.update(processTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "publish/{processTypeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessTypeDTO> publish(@PathVariable Long processTypeId) {
        ProcessTypeDTO response = service.publish(processTypeId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "create-new-version/{processTypeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessTypeDTO> createNewVersion(@PathVariable Long processTypeId) {
        ProcessTypeDTO response = service.createNewVersion(processTypeId);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "{processTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long processTypeId) {
        service.delete(processTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
