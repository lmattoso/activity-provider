package com.activity.provider.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class ActivityDeploymentResponseDTO {
    private String url;
}