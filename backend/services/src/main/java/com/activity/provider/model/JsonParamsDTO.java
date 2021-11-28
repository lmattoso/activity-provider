package com.activity.provider.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@ToString(callSuper=true)
@SuperBuilder
public class JsonParamsDTO {
    private String subject;
    private String level;
    private String question;
    private String response;
    private String createdBy;
}
