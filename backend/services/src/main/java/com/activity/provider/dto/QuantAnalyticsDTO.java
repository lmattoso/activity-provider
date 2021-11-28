package com.activity.provider.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class QuantAnalyticsDTO {
    private String name;
    private String value;
}