package com.activity.provider.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true)
@Builder
public class JsonParamsDTO {
    private String subject;
    private String level;
    private String question;
    private String response;
    private String createdBy;
}
