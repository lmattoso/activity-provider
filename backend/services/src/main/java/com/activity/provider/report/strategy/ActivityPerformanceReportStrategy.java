package com.activity.provider.report.strategy;

import com.activity.provider.model.StudentAnswer;
import com.activity.provider.report.*;
import com.activity.provider.report.system.*;
import com.activity.provider.util.Util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityPerformanceReportStrategy implements ReportStrategy {

    @Value("${logo.activity-performance}")
    private String logo;

    private static final List<ReportItemHeader> headers = Arrays.asList(
            ReportItemHeader.builder().name("Ivenira ID").colSpan(6).alignment(Element.ALIGN_LEFT).build(),
            ReportItemHeader.builder().name("Acertou?").colSpan(1).alignment(Element.ALIGN_RIGHT).build()
    );

    @Override
    public String getLogo() {
        return this.logo;
    }

    @Override
    public void createHeader(ReportSubSystemRepresentation reportSubSystem, Document document, ReportParams params) throws DocumentException, IOException, URISyntaxException {
        reportSubSystem.createHeader(document, Arrays.asList(
            ReportHeaderField.builder().name("Activity ID:")
                .value(params.getActivityID())
                .width(3)
                .fieldWidth(9)
                .build(),
            ReportHeaderField.builder().name("Data:")
                .value(Util.convertLocalDateTimeToStringFormatted(ZonedDateTime.now(), Util.DATE_MASK))
                .width(2)
                .fieldWidth(2)
                .build()
        ));
    }

    @Override
    public void createContent(ReportSubSystemRepresentation reportSubSystem, Document document, ReportParams params) throws DocumentException, IOException, URISyntaxException {
        List<StudentAnswer> answers = params.getAnswers();

        long total = answers.size();
        long value = answers.stream()
            .filter(StudentAnswer::isCorrect)
            .count();

        reportSubSystem.createParagraph(document, "Descrição: ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        reportSubSystem.createItems(headers, this.convertAnswers(answers), document, value, total);
    }

    private List<ReportItemRow> convertAnswers(List<StudentAnswer> answers) {
        return answers.stream().
            map(answer ->
                ReportItemRow.builder()
                    .items(Arrays.asList(
                        ReportItem.build(answer.getInveniraStdID().toString(), Element.ALIGN_LEFT),
                        ReportItem.build(answer.isCorrect() ? "Sim" : "Não", Element.ALIGN_RIGHT)
                    )).build()
            )
            .collect(Collectors.toList());
    }
}