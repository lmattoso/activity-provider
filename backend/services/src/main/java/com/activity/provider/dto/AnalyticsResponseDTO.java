package com.activity.provider.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class AnalyticsResponseDTO {
    private Long inveniraStdID;
    private List<QuantAnalyticsDTO> quantAnalytics;
    private QualAnalyticsDTO qualAnalytics;
}