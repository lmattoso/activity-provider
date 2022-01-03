package com.activity.provider.service;

import com.activity.provider.dto.AnalyticsDataDTO;
import com.activity.provider.model.Activity;
import com.activity.provider.model.StudentAnswer;
import com.activity.provider.repository.ActivityRepository;
import com.activity.provider.repository.StudentAnswerRepository;
import com.activity.provider.util.SingletonAnalyticsProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActivityService {

    private final ActivityRepository repository;
    private final StudentAnswerRepository studentAnswerRepository;

    public Activity getByActivityID(String activityID) {
        return repository.getById(activityID);
    }

    public List<Activity> getAll() {
        return repository.getAll();
    }

    public AnalyticsDataDTO generateStudentAnalytics(Long inveniraStdID) {
        List<StudentAnswer> answers = studentAnswerRepository.getAnswersByInveniraStdID(inveniraStdID);
        return SingletonAnalyticsProcessor.getInstance().generateStudentAnalytics(answers, inveniraStdID);
    }
}