package com.activity.provider.service;

import com.activity.provider.model.StudentAnswer;
import com.activity.provider.repository.StudentAnswerRepository;
import com.activity.provider.util.ReportFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private final ReportFacade reportFacade;
    private final StudentAnswerRepository studentAnswerRepository;

    public byte[] generatePerformanceReport(Long inveniraStdID) {
        List<StudentAnswer> answers = studentAnswerRepository.getAnswersByInveniraStdID(inveniraStdID);
        return reportFacade.generatePerformanceReport(inveniraStdID, answers);
    }
}