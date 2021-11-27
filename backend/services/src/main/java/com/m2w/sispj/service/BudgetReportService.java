package com.m2w.sispj.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.m2w.sispj.domain.budget.Budget;
import com.m2w.sispj.domain.budget.BudgetItem;
import com.m2w.sispj.util.BudgetReportItemHeader;
import com.m2w.sispj.util.SISPJUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BudgetReportService {

    @Value("${sispj.budget-report-logo}")
    private String logo;

    private static final List<BudgetReportItemHeader> headers = Arrays.asList(
        BudgetReportItemHeader.builder().name("Pedido").colSpan(4).alignment(Element.ALIGN_LEFT).build(),
        BudgetReportItemHeader.builder().name("Valor").colSpan(1).alignment(Element.ALIGN_RIGHT).build(),
        BudgetReportItemHeader.builder().name("IVA 23%").colSpan(1).alignment(Element.ALIGN_RIGHT).build(),
        BudgetReportItemHeader.builder().name("Total").colSpan(1).alignment(Element.ALIGN_RIGHT).build()
    );

    public byte[] generateReport(Budget budget) {
        try {
            Document document = new Document();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, stream);

            document.open();

            createLogo(document);
            createHeader(budget, document);

            createParagraph(document, "Pedido:", budget.getRequestDescription());
            createParagraph(document, "Serviços:", budget.getServiceDescription());
            createParagraph(document, "Documentos:", budget.getDocumentsDescription());

            createItems(budget, document);

            createParagraph(document, "Observações:", budget.getComments());

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

    private void createHeader(Budget budget, Document document) throws DocumentException {
        PdfPTable layout = new PdfPTable(16);
        layout.setSpacingBefore(10);
        layout.setSpacingAfter(10);
        layout.setWidthPercentage(100);

        createHeaderLine(layout, "Nome do cliente:", budget.getName(), 3, 9);
        createHeaderLine(layout, "Data:", SISPJUtil.convertLocalDateTimeToStringFormatted(ZonedDateTime.now(), SISPJUtil.DATE_MASK), 2, 2);
        createHeaderLine(layout, "Morada:", budget.getAddress(), 3, 9);
        createHeaderLine(layout, "Validade:", SISPJUtil.convertLocalDateTimeToStringFormatted(budget.getExpirationDate(), SISPJUtil.DATE_MASK), 2, 2);
        createHeaderLine(layout, "Contacto:", budget.getPhone(), 3, 13);
        createHeaderLine(layout, "NIF:", budget.getNif(), 3, 13);
        createHeaderLine(layout, "NISS:", budget.getNiss(), 3, 13);
        createHeaderLine(layout, "Atendido(a) por:", budget.getCreateBy().getName(), 3, 13);

        document.add(layout);
    }

    private void createItems(Budget budget, Document document) throws DocumentException, IOException, URISyntaxException {
        if(budget != null && !budget.getItems().isEmpty()) {
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            addTableHeader(table);
            addRows(table, budget);
            addTotal(table, budget);
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

    private void addRows(PdfPTable table, Budget budget) {
        budget.getItems().forEach(budgetItemDTO -> {
            addCell(table, budgetItemDTO.getRequest(), Element.ALIGN_LEFT, headers.get(0).getColSpan());
            addCell(table, SISPJUtil.formatCurrency(budgetItemDTO.getValue(), "pt", "PT"), Element.ALIGN_RIGHT, headers.get(1).getColSpan());
            addCell(table, SISPJUtil.formatCurrency(budgetItemDTO.getIva(), "pt", "PT"), Element.ALIGN_RIGHT, headers.get(2).getColSpan());
            addCell(table, SISPJUtil.formatCurrency(budgetItemDTO.getTotal(), "pt", "PT"), Element.ALIGN_RIGHT, headers.get(3).getColSpan());
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

    private void addTotal(PdfPTable table, Budget budget) {
        Font font = new Font();
        font.setColor(BaseColor.WHITE);
        font.setStyle(Font.BOLD);
        font.setSize(11);

        BigDecimal total = budget.getItems().stream()
            .map(BudgetItem::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        addCell(table, "TOTAL:", Element.ALIGN_RIGHT, 6, font, PdfPCell.NO_BORDER, BaseColor.GRAY, 25);
        addCell(table, SISPJUtil.formatCurrency(total, "pt", "PT"), Element.ALIGN_RIGHT, 1, font, PdfPCell.NO_BORDER, BaseColor.GRAY, 25);
    }
}