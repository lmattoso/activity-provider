package com.activity.provider.model;

import com.activity.provider.model.enums.ActivityType;
import com.activity.provider.model.enums.Level;
import com.activity.provider.model.enums.Subject;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Activity {
    private String activityID;
    private ActivityType type;
    private Subject subject;
    private Level level;
    private String question;
    private String response;
    private String createdBy;
}