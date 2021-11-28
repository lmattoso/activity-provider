package com.activity.provider.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class AnalyticsRequestDTO {
    private String activityID;
}