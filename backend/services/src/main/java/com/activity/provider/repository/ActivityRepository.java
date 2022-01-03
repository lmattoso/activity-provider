package com.activity.provider.repository;

import com.activity.provider.model.Activity;
import com.activity.provider.model.MultipleChoiceActivity;
import com.activity.provider.model.enums.ActivityType;
import com.activity.provider.model.enums.Level;
import com.activity.provider.model.enums.Subject;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ActivityRepository {

    private static final Map<String, Activity> data;

    public static final String ACTIVITY_A000001 = "A000001";
    public static final String ACTIVITY_A000002 = "A000002";
    public static final String ACTIVITY_A000003 = "A000003";
    public static final String ACTIVITY_A000004 = "A000004";
    public static final String ACTIVITY_A000005 = "A000005";
    public static final String ACTIVITY_A000006 = "A000006";
    public static final String ACTIVITY_A000007 = "A000007";
    public static final String ACTIVITY_A000008 = "A000008";
    public static final String ACTIVITY_A000009 = "A000009";

    static {
        data = new HashMap<>();

        addActivityData(ACTIVITY_A000001, ActivityType.FILL_THE_BLANK, Subject.INITIAL_CONCEPTS, Level.EASY, "______________, sometimes shortened to InfoSec, is the practice of protecting information by mitigating information risks", "Information Security");
        addMultipleChoiceActivityData(ACTIVITY_A000002, ActivityType.MULTIPLE_CHOICE, Subject.INITIAL_CONCEPTS, Level.EASY, "What is the practice of protecting information by mitigating information risks_", "B", Arrays.asList("Software", "Information Security", "Hardware", "Artificial Intelligence"));
        addActivityData(ACTIVITY_A000003, ActivityType.TRUE_FALSE, Subject.INITIAL_CONCEPTS, Level.EASY, "Information Security, sometimes shortened to InfoSec, is the practice of protecting information by mitigating information risks", "TRUE");
        addActivityData(ACTIVITY_A000004, ActivityType.FILL_THE_BLANK, Subject.INITIAL_CONCEPTS, Level.EASY, "______________, sometimes shortened to InfoSec, is the practice of protecting information by mitigating information risks", "Information Security");
        addMultipleChoiceActivityData(ACTIVITY_A000005, ActivityType.MULTIPLE_CHOICE, Subject.INITIAL_CONCEPTS, Level.EASY, "What is the practice of protecting information by mitigating information risks_", "B", Arrays.asList("Software", "Information Security", "Hardware", "Artificial Intelligence"));
        addActivityData(ACTIVITY_A000006, ActivityType.TRUE_FALSE, Subject.INITIAL_CONCEPTS, Level.EASY, "Information Security, sometimes shortened to InfoSec, is the practice of protecting information by mitigating information risks", "TRUE");
        addActivityData(ACTIVITY_A000007, ActivityType.FILL_THE_BLANK, Subject.INITIAL_CONCEPTS, Level.EASY, "______________, sometimes shortened to InfoSec, is the practice of protecting information by mitigating information risks", "Information Security");
        addMultipleChoiceActivityData(ACTIVITY_A000008, ActivityType.MULTIPLE_CHOICE, Subject.INITIAL_CONCEPTS, Level.EASY, "What is the practice of protecting information by mitigating information risks_", "B", Arrays.asList("Software", "Information Security", "Hardware", "Artificial Intelligence"));
        addActivityData(ACTIVITY_A000009, ActivityType.TRUE_FALSE, Subject.INITIAL_CONCEPTS, Level.EASY, "Information Security, sometimes shortened to InfoSec, is the practice of protecting information by mitigating information risks", "TRUE");
    }

    private static void addActivityData(String activityID, ActivityType type, Subject subject, Level level, String question, String response) {
        data.put(activityID, Activity.builder()
            .activityID(activityID)
            .type(type)
            .subject(subject)
            .level(level)
            .question(question)
            .response(response)
            .build());
    }

    private static void addMultipleChoiceActivityData(String activityID, ActivityType type, Subject subject, Level level,
                                                      String question, String response, List<String> options) {
        data.put(activityID, MultipleChoiceActivity.builder()
            .activityID(activityID)
            .type(type)
            .subject(subject)
            .level(level)
            .options(options)
            .question(question)
            .response(response)
            .build());
    }

    public Activity getById(String activityID) {
        return data.get(activityID);
    }

    public List<Activity> getAll() {
        return new ArrayList<>(data.values());
    }
}
