package com.m2w.sispj.controller;

import com.m2w.sispj.dto.budget.BudgetDTO;
import com.m2w.sispj.service.BudgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("budget")
@Slf4j
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @GetMapping(value = "report/{budgetId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport(@PathVariable Long budgetId) {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"Resume" + LocalDateTime.now().toLocalDate() + ".pdf\""
            ).body(service.downloadReport(budgetId));
    }

    @GetMapping(value = "{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetDTO> readById(@PathVariable Long budgetId) {
        return ResponseEntity.ok().body(service.findById(budgetId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetDTO> create(@RequestBody BudgetDTO budgetDTO) {
        BudgetDTO response = service.create(budgetDTO);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BudgetDTO> update(@RequestBody BudgetDTO budgetDTO) {
        BudgetDTO response = service.update(budgetDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BudgetDTO>> list(@PageableDefault(size = Integer.MAX_VALUE) @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(service.list(pageable));
    }

    @DeleteMapping(value = "{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long budgetId) {
        service.delete(budgetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}