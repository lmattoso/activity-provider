package com.activity.provider.service;

import com.activity.provider.model.StudentAnswer;
import com.activity.provider.report.ReportFacade;
import com.activity.provider.report.ReportParams;
import com.activity.provider.report.strategy.ReportType;
import com.activity.provider.repository.StudentAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportFacade reportFacade;
    private final StudentAnswerRepository studentAnswerRepository;

    public byte[] generateStudentPerformanceReport(Long inveniraStdID) {
        List<StudentAnswer> answers = studentAnswerRepository.getAnswersByInveniraStdID(inveniraStdID);
        return reportFacade.generateReport(ReportParams.builder()
            .reportType(ReportType.STUDENT_PERFORMANCE)
            .inveniraStdID(inveniraStdID)
            .answers(answers).build());
    }

    public byte[] generateActivityPerformanceReport(String activityID) {
        List<StudentAnswer> answers = studentAnswerRepository.getAnswersByActivity(activityID);
        return reportFacade.generateReport(ReportParams.builder()
            .reportType(ReportType.ACTIVITY_PERFORMANCE)
            .activityID(activityID)
            .answers(answers).build());
    }
}