package com.activity.provider.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Builder
public class AnalyticsRequestDTO {
    private String activityID;
}