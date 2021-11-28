package com.activity.provider.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class AnalyticsRequestDTO {
    private String activityID;
}