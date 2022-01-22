package com.activity.provider.report.system;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ReportSubSystemRepresentation {

    public void createLogo(Document document, String logo) throws IOException, DocumentException, URISyntaxException {
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

    public void createHeader(Document document, List<ReportHeaderField> headers) throws DocumentException {
        PdfPTable layout = new PdfPTable(16);
        layout.setSpacingBefore(10);
        layout.setSpacingAfter(10);
        layout.setWidthPercentage(100);

        headers.forEach(header -> createHeaderLine(layout, header));

        document.add(layout);
    }

    public void createItems(Document document, List<ReportItemHeader> headers, List<ReportItemRow> rows, long value, long total) throws DocumentException {
        if(rows != null && !rows.isEmpty()) {
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            this.addTableHeader(table, headers);
            this.addRows(table, headers, rows);
            this.addTotal(table, value, total);
            table.setSpacingBefore(15);
            table.setSpacingAfter(15);
            document.add(table);
        }
    }

    private void createHeaderLine(PdfPTable layout, ReportHeaderField headerField) {
        Font font = new Font();
        font.setSize(10.5f);
        font.setStyle(Font.BOLD);

        PdfPCell cell = new PdfPCell();
        Phrase content = new Phrase(headerField.getName(), font);
        cell.setPhrase(content);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(headerField.getFieldWidth());
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        layout.addCell(cell);

        Font font2 = new Font();
        font2.setSize(10);

        cell = new PdfPCell();
        cell.setColspan(headerField.getWidth());
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(Optional.ofNullable(headerField.getValue()).orElse("-  "), font2));
        layout.addCell(cell);
    }

    public void createParagraph(Document document, String label, String value) throws DocumentException {
        if(!ObjectUtils.isEmpty(value)) {
            PdfPTable layout = new PdfPTable(16);
            layout.setSpacingBefore(10);
            layout.setSpacingAfter(10);
            layout.setWidthPercentage(100);

            createHeaderLine(layout, ReportHeaderField.builder()
                .name(label)
                .value(value)
                .fieldWidth(3)
                .width(13)
                .build());

            document.add(layout);
        }
    }

    private void addTableHeader(PdfPTable table, List<ReportItemHeader> headers) {
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

    private void addRows(PdfPTable table, List<ReportItemHeader> headers, List<ReportItemRow> rows) {
        rows.forEach(row -> {
            for(int i = 0; i < row.getItems().size(); i++) {
                ReportItem item = row.getItems().get(i);
                addCell(table, item.getValue(), item.getAlignment(), headers.get(0).getColSpan());
            }
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

    private void addTotal(PdfPTable table, long value, long total) {
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        font.setStyle(Font.BOLD);
        font.setSize(11);

        addCell(table, "TOTAL:", Element.ALIGN_RIGHT, 6, font, PdfPCell.NO_BORDER, BaseColor.GRAY, 25);
        addCell(table, String.format("%d de %d", value, total), Element.ALIGN_RIGHT, 1, font, PdfPCell.NO_BORDER, BaseColor.GRAY, 25);
    }

    public void createContent(Document document, ReportContent reportContent) throws DocumentException {
        this.createParagraph(document, "Descrição: ", reportContent.getDescription());
        this.createItems(document, reportContent.getHeaders(), reportContent.getRows(), reportContent.getCurrent(), reportContent.getTotal());
    }
}