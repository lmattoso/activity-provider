package com.m2w.sispj.controller;

import com.m2w.sispj.dto.DocumentTypeDTO;
import com.m2w.sispj.service.DocumentTypeService;
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
@RequestMapping("document-type")
@Slf4j
public class DocumentTypeController {

    private final DocumentTypeService service;

    public DocumentTypeController(DocumentTypeService service) {
        this.service = service;
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentTypeDTO>> listDocumentTypes(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @GetMapping(value = "{documentTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentTypeDTO> readById(@PathVariable Long documentTypeId) {
        return ResponseEntity.ok().body(service.findById(documentTypeId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentTypeDTO> create(@RequestBody DocumentTypeDTO processTypeDTO) {
        DocumentTypeDTO response = service.create(processTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentTypeDTO> update(@RequestBody DocumentTypeDTO documentTypeDTO) {
        DocumentTypeDTO response = service.update(documentTypeDTO);
        return ResponseEntity.ok().body(response);
    }

    //@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "{documentTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable Long documentTypeId) {
        service.delete(documentTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}