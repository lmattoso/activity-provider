package com.activity.provider.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class ActivityDeploymentRequestDTO {
    private String activityID;
    private Long inveniraStdID;
    private JsonParamsDTO json_params;
}