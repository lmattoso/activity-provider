package com.activity.provider.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Builder
public class ActivityDeploymentRequestDTO {
    private String activityID;
    private Long inveniraStdID;
    private JsonParamsDTO json_params;
}