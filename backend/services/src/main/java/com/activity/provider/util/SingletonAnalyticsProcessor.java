package com.activity.provider.util;

import com.activity.provider.dto.AnalyticsDataDTO;
import com.activity.provider.dto.QualAnalyticsDTO;
import com.activity.provider.dto.QuantAnalyticsDTO;
import com.activity.provider.model.StudentAnswer;

import java.util.Arrays;
import java.util.List;

public class SingletonAnalyticsProcessor {

    private static SingletonAnalyticsProcessor generator;

    private SingletonAnalyticsProcessor() { }

    public static SingletonAnalyticsProcessor getInstance() {
        if(generator == null) {
            generator = new SingletonAnalyticsProcessor();
        }

        return generator;
    }

    public AnalyticsDataDTO generateStudentAnalytics(List<StudentAnswer> answers, Long inveniraStdID) {
        long totalAnswers = answers.size();
        long correctAnswers = answers.stream().filter(StudentAnswer::isCorrect).count();

        String performance = "100,00%";

        if(totalAnswers != 0) {
            performance = String.format("%.2f", (100.0 * correctAnswers / totalAnswers));
        }

        return AnalyticsDataDTO.builder()
            .inveniraStdID(inveniraStdID)
            .quantAnalytics(Arrays.asList(
                QuantAnalyticsDTO.builder().name("Total de Tentativas").value(String.valueOf(totalAnswers)).build(),
                QuantAnalyticsDTO.builder().name("Respostas Certas").value(String.valueOf(correctAnswers)).build(),
                QuantAnalyticsDTO.builder().name("Taxa de Acerto (%)").value(performance).build()
            ))
            .qualAnalytics(QualAnalyticsDTO.builder()
                .studentActivityProfile(String.format("https://ActivityProvider/profile/?APAnID=%d", inveniraStdID))
                .activityHeatMap(String.format("https://ActivityProvider/heatmap/?APAnID=%d", inveniraStdID))
                .build()
            )
            .build();
    }
}