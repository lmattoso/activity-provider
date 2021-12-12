package com.activity.provider.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Builder
public class AnalyticsDataDTO {
    private Long inveniraStdID;
    private List<QuantAnalyticsDTO> quantAnalytics;
    private QualAnalyticsDTO qualAnalytics;
}