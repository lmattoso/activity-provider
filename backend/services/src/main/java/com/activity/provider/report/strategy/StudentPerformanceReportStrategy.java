package com.activity.provider.report.strategy;

import com.activity.provider.model.StudentAnswer;
import com.activity.provider.report.ReportParams;
import com.activity.provider.report.system.*;
import com.activity.provider.util.Util;
import com.itextpdf.text.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentPerformanceReportStrategy implements ReportStrategy {

    @Value("${logo.student-performance}")
    private String logo;

    private static final List<ReportItemHeader> headers = Arrays.asList(
        ReportItemHeader.builder().name("Atividade").colSpan(6).alignment(Element.ALIGN_LEFT).build(),
        ReportItemHeader.builder().name("Acertou?").colSpan(1).alignment(Element.ALIGN_RIGHT).build()
    );

    @Override
    public String getLogoName() {
        return this.logo;
    }

    @Override
    public List<ReportHeaderField> getHeaderContent(ReportParams params) {
        return Arrays.asList(
            ReportHeaderField.builder().name("Ivenira ID:")
                .value(params.getInveniraStdID().toString())
                .width(3)
                .fieldWidth(9)
                .build(),
            ReportHeaderField.builder().name("Data:")
                .value(Util.convertLocalDateTimeToStringFormatted(ZonedDateTime.now(), Util.DATE_MASK))
                .width(2)
                .fieldWidth(2)
                .build()
        );
    }

    @Override
    public ReportContent getReportContent(ReportParams params) {
        List<StudentAnswer> answers = params.getAnswers();

        long total = answers.size();
        long value = answers.stream()
            .filter(StudentAnswer::isCorrect)
            .count();

        return ReportContent.builder()
            .headers(headers)
            .rows(this.convertAnswers(answers))
            .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
            .total(total)
            .current(value)
            .build();
    }

    private List<ReportItemRow> convertAnswers(List<StudentAnswer> answers) {
        return answers.stream().
            map(answer ->
                ReportItemRow.builder()
                    .items(Arrays.asList(
                        ReportItem.build(answer.getActivityID(), Element.ALIGN_LEFT),
                        ReportItem.build(answer.isCorrect() ? "Sim" : "Não", Element.ALIGN_RIGHT)
                    )).build()
            )
            .collect(Collectors.toList());
    }
}