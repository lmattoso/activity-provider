package com.activity.provider.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Builder
public class QualAnalyticsDTO {
    private String studentActivityProfile;
    private String activityHeatMap;
}