package com.activity.provider.report.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportContent {
    private String description;
    private List<ReportItemHeader> headers;
    private List<ReportItemRow> rows;
    private Long total;
    private Long current;
}