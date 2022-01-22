package com.activity.provider.service;

import com.activity.provider.model.StudentAnswer;
import com.activity.provider.report.ReportFacade;
import com.activity.provider.report.ReportParams;
import com.activity.provider.report.strategy.ReportStrategy;
import com.activity.provider.report.strategy.ReportType;
import com.activity.provider.repository.StudentAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportFacade reportFacade;
    private final StudentAnswerRepository studentAnswerRepository;
    private Map<ReportType, ReportStrategy> reportStrategies;

    public byte[] generateStudentPerformanceReport(Long inveniraStdID) {
        List<StudentAnswer> answers = studentAnswerRepository.getAnswersByInveniraStdID(inveniraStdID);

        return reportFacade.generateReport(
            ReportParams.builder().inveniraStdID(inveniraStdID).answers(answers).build(),
            getStrategy(ReportType.STUDENT_PERFORMANCE)
        );
    }

    public byte[] generateActivityPerformanceReport(String activityID) {
        List<StudentAnswer> answers = studentAnswerRepository.getAnswersByActivity(activityID);

        return reportFacade.generateReport(
            ReportParams.builder().activityID(activityID).answers(answers).build(),
            getStrategy(ReportType.ACTIVITY_PERFORMANCE)
        );
    }

    private ReportStrategy getStrategy(ReportType type) {
        return Optional.ofNullable(reportStrategies.get(type)).orElseThrow();
    }
}