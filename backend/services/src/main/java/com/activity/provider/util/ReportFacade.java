package com.activity.provider.util;

import com.activity.provider.model.StudentAnswer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
public class ReportFacade {

    private ReportSubSystemRepresentation reportSubSystem;

    private static final List<ReportItemHeader> headers = Arrays.asList(
        ReportItemHeader.builder().name("Atividade").colSpan(6).alignment(Element.ALIGN_LEFT).build(),
        ReportItemHeader.builder().name("Acertou?").colSpan(1).alignment(Element.ALIGN_RIGHT).build()
    );

    public byte[] generatePerformanceReport(Long inveniraStdID, List<StudentAnswer> answers) {
        try {
            Document document = new Document();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, stream);

            document.open();

            long total = answers.size();

            long value = answers.stream()
                .filter(StudentAnswer::isCorrect)
                .count();

            reportSubSystem.createLogo(document);
            reportSubSystem.createHeader(inveniraStdID, document);
            reportSubSystem.createParagraph(document, "Descrição: ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            reportSubSystem.createItems(headers, this.convertAnswers(answers), document, value, total);

            document.close();

            return stream.toByteArray();
        } catch (DocumentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<ReportItemRow> convertAnswers(List<StudentAnswer> answers) {
        return answers.stream().
            map(answer ->
                ReportItemRow.builder()
                    .items(Arrays.asList(
                        ReportItem.build(answer.getActivityID(), Element.ALIGN_LEFT),
                        ReportItem.build(answer.isCorrect() ? "Sim" : "Não", Element.ALIGN_RIGHT)
                    ))
                    .build()
            )
            .collect(Collectors.toList());
    }
}