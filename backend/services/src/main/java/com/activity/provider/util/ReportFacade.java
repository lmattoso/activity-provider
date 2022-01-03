package com.activity.provider.util;

import com.activity.provider.model.StudentAnswer;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportFacade {

    @Value("${report-logo}")
    private String logo;

    private static final List<BudgetReportItemHeader> headers = Arrays.asList(
        BudgetReportItemHeader.builder().name("Atividade").colSpan(6).alignment(Element.ALIGN_LEFT).build(),
        BudgetReportItemHeader.builder().name("Acertou?").colSpan(1).alignment(Element.ALIGN_RIGHT).build()
    );

    public byte[] generatePerformanceReport(Long inveniraStdID, List<StudentAnswer> answers) {
        try {
            Document document = new Document();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, stream);

            document.open();

            createLogo(document);
            createHeader(inveniraStdID, document);

            createParagraph(document, "Descrição: ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

            createItems(answers, document);

            document.close();

            return stream.toByteArray();
        } catch (DocumentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createLogo(Document document) throws IOException, DocumentException, URISyntaxException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(String.format("img/%s", logo));

        if (url != null) {
            log.info("File exists: {}", url);
            Image img = Image.getInstance(url);
            img.scalePercent(60f);
            document.add(img);
        } else {
            log.info("File not exists:");
        }
    }

    private void createHeader(Long inveniraStdID, Document document) throws DocumentException {
        PdfPTable layout = new PdfPTable(16);
        layout.setSpacingBefore(10);
        layout.setSpacingAfter(10);
        layout.setWidthPercentage(100);

        createHeaderLine(layout, "Ivenira ID:", inveniraStdID.toString(), 3, 9);
        createHeaderLine(layout, "Data:", Util.convertLocalDateTimeToStringFormatted(ZonedDateTime.now(), Util.DATE_MASK), 2, 2);

        document.add(layout);
    }

    private void createItems(List<StudentAnswer> answers, Document document) throws DocumentException, IOException, URISyntaxException {
        if(answers != null && !answers.isEmpty()) {
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            addTableHeader(table);
            addRows(table, answers);
            addTotal(table, answers);
            table.setSpacingBefore(15);
            table.setSpacingAfter(15);
            document.add(table);
        }
    }

    private void createHeaderLine(PdfPTable layout, String field, String value, int fieldWidth, int width) {
        Font font = new Font();
        font.setSize(10.5f);
        font.setStyle(Font.BOLD);

        PdfPCell cell = new PdfPCell();
        Phrase content = new Phrase(field, font);
        cell.setPhrase(content);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(fieldWidth);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        layout.addCell(cell);

        Font font2 = new Font();
        font2.setSize(10);

        cell = new PdfPCell();
        cell.setColspan(width);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(Optional.ofNullable(value).orElse("-  "), font2));
        layout.addCell(cell);
    }

    private void createParagraph(Document document, String label, String value) throws DocumentException {
        if(!ObjectUtils.isEmpty(value)) {
            PdfPTable layout = new PdfPTable(16);
            layout.setSpacingBefore(10);
            layout.setSpacingAfter(10);
            layout.setWidthPercentage(100);

            createHeaderLine(layout, label, value, 3, 13);

            document.add(layout);
        }
    }

    private void addTableHeader(PdfPTable table) {
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        font.setStyle(Font.BOLD);
        font.setSize(10);

        headers.forEach(headerDefinition -> {
            PdfPCell header = new PdfPCell();
            header.setFixedHeight(25);
            header.setColspan(headerDefinition.getColSpan());
            header.setHorizontalAlignment(headerDefinition.getAlignment());
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setBackgroundColor(BaseColor.GRAY);
            header.setBorder(PdfPCell.NO_BORDER);
            header.setBorderWidth(0);
            header.setPhrase(new Phrase(headerDefinition.getName(), font));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, List<StudentAnswer> answers) {
        answers.forEach(answer -> {
            addCell(table, answer.getActivityID(), Element.ALIGN_LEFT, headers.get(0).getColSpan());
            addCell(table, answer.isCorrect() ? "Sim" : "Não", Element.ALIGN_RIGHT, headers.get(1).getColSpan());
        });
    }

    private void addCell(PdfPTable table, String value, int horizontalAlignment, int colSpan) {
        Font font = new Font();
        font.setSize(9.5f);
        addCell(table, value, horizontalAlignment, colSpan, font, 3, BaseColor.WHITE, 20);
    }

    private void addCell(PdfPTable table, String value, int horizontalAlignment, int colSpan, Font font, int border, BaseColor backgroundColor, int fixedHeight) {
        PdfPCell cell = new PdfPCell(new Phrase(value, font));
        cell.setFixedHeight(fixedHeight);
        cell.setColspan(colSpan);
        cell.setHorizontalAlignment(horizontalAlignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(border);
        cell.setBackgroundColor(backgroundColor);
        table.addCell(cell);
    }

    private void addTotal(PdfPTable table, List<StudentAnswer> answers) {
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        font.setStyle(Font.BOLD);
        font.setSize(11);

        long total = answers.size();

        long correct = answers.stream()
            .filter(StudentAnswer::isCorrect)
            .count();

        addCell(table, "TOTAL:", Element.ALIGN_RIGHT, 6, font, PdfPCell.NO_BORDER, BaseColor.GRAY, 25);
        addCell(table, String.format("%d de %d", correct, total), Element.ALIGN_RIGHT, 1, font, PdfPCell.NO_BORDER, BaseColor.GRAY, 25);
    }
}