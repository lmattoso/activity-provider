package com.activity.provider.controller;

import com.activity.provider.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("activity")
@Slf4j
public class IveniraController {

    @GetMapping(value = "configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityConfigurationResponseDTO> getActivityConfiguration() {
        return ResponseEntity.ok().body(ActivityConfigurationResponseDTO.builder()
            .url("https://ActivityProvider/configuration")
            .build());
    }

    @PostMapping(value = "deploy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDeploymentResponseDTO> deploy(@RequestBody ActivityDeploymentRequestDTO deploymentDTO) {
        return ResponseEntity.ok().body(ActivityDeploymentResponseDTO.builder()
            .url("https://ActivityProvider/deploy")
            .build());
    }

    @PostMapping(value = "analytics", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyticsResponseDTO> analytics(@RequestBody AnalyticsRequestDTO analyticsDTO) {
        return ResponseEntity.ok().body(AnalyticsResponseDTO.builder()
            .inveniraStdID(1001l)
            .quantAnalytics(Arrays.asList(
                QuantAnalyticsDTO.builder().name("Acedeu à atividade").value("true").build(),
                QuantAnalyticsDTO.builder().name("Download documento 1").value("true").build(),
                QuantAnalyticsDTO.builder().name("Evolução pela atividade (%)").value("33.3").build()
            ))
            .qualAnalytics(QualAnalyticsDTO.builder()
                .studentActivityProfile("https://ActivityProvider/?APAnID=11111111")
                .activityHeatMap("https://ActivityProvider/?APAnID=21111111")
                .build()
            )
            .build());
    }
}