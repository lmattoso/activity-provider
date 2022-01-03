package com.activity.provider.repository;

import com.activity.provider.model.StudentAnswer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentAnswerRepository {

    private static final List<StudentAnswer> mockedData;

    public static final Long STUDENT_000001 = 10000L;
    public static final Long STUDENT_000002 = 20000L;
    public static final Long STUDENT_000003 = 30000L;

    static {
        mockedData = new ArrayList<>();

        addActivityData(ActivityRepository.ACTIVITY_A000001, STUDENT_000001, true);
        addActivityData(ActivityRepository.ACTIVITY_A000002, STUDENT_000001, false);
        addActivityData(ActivityRepository.ACTIVITY_A000003, STUDENT_000001, true);
        addActivityData(ActivityRepository.ACTIVITY_A000004, STUDENT_000001, true);
        addActivityData(ActivityRepository.ACTIVITY_A000005, STUDENT_000001, false);
        addActivityData(ActivityRepository.ACTIVITY_A000006, STUDENT_000001, true);
        addActivityData(ActivityRepository.ACTIVITY_A000007, STUDENT_000001, true);
        addActivityData(ActivityRepository.ACTIVITY_A000008, STUDENT_000001, false);
        addActivityData(ActivityRepository.ACTIVITY_A000009, STUDENT_000001, true);

        addActivityData(ActivityRepository.ACTIVITY_A000001, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000002, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000003, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000004, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000005, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000006, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000007, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000008, STUDENT_000002, true);
        addActivityData(ActivityRepository.ACTIVITY_A000009, STUDENT_000002, true);

        addActivityData(ActivityRepository.ACTIVITY_A000001, STUDENT_000003, true);
        addActivityData(ActivityRepository.ACTIVITY_A000002, STUDENT_000003, false);
        addActivityData(ActivityRepository.ACTIVITY_A000003, STUDENT_000003, false);
        addActivityData(ActivityRepository.ACTIVITY_A000004, STUDENT_000003, true);
        addActivityData(ActivityRepository.ACTIVITY_A000005, STUDENT_000003, false);
        addActivityData(ActivityRepository.ACTIVITY_A000006, STUDENT_000003, false);
        addActivityData(ActivityRepository.ACTIVITY_A000007, STUDENT_000003, true);
        addActivityData(ActivityRepository.ACTIVITY_A000008, STUDENT_000003, false);
        addActivityData(ActivityRepository.ACTIVITY_A000009, STUDENT_000003, false);
    }

    private static void addActivityData(String activityID, Long inveniraStdID, boolean correct) {
        mockedData.add(StudentAnswer.builder()
            .activityID(activityID)
            .inveniraStdID(inveniraStdID)
            .correct(correct)
            .build());
    }

    public List<StudentAnswer> getAnswersByActivity(String activityID) {
        return mockedData.stream()
            .filter(answer -> answer.getActivityID().equals(activityID))
            .collect(Collectors.toList());
    }

    public List<StudentAnswer> getAnswersByInveniraStdID(Long inveniraStdID) {
        return mockedData.stream()
            .filter(answer -> answer.getInveniraStdID().equals(inveniraStdID))
            .collect(Collectors.toList());
    }
}
