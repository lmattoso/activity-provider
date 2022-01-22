package com.activity.provider.report;

import com.activity.provider.model.StudentAnswer;
import com.activity.provider.report.strategy.ReportType;
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
public class ReportParams {
    private Long inveniraStdID;
    private String activityID;
    private List<StudentAnswer> answers;
    private ReportType reportType;
}