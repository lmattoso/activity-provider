package com.activity.provider.controller;

import com.activity.provider.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("report")
@Slf4j
@AllArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping(value = "{inveniraStdID}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport(@PathVariable Long inveniraStdID) {
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=\"report_student_performance" + LocalDateTime.now().toLocalTime() + ".pdf\""
            ).body(service.generatePerformanceReport(inveniraStdID));
    }
}