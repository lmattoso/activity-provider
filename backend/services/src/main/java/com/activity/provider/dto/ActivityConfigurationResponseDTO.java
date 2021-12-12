package com.activity.provider.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Builder
public class ActivityConfigurationResponseDTO {
    private String url;
}