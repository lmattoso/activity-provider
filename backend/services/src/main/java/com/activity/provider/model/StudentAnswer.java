package com.activity.provider.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class StudentAnswer {
    private String activityID;
    private Long inveniraStdID;
    private boolean correct;
}