package com.activity.provider.report;

import com.activity.provider.report.strategy.ReportStrategy;
import com.activity.provider.report.system.ReportSubSystemRepresentation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Slf4j
@AllArgsConstructor
public class ReportFacade {

    private ReportSubSystemRepresentation reportSubSystem;

    public byte[] generateReport(ReportParams params, ReportStrategy strategy) {
        try {
            Document document = new Document();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, stream);

            document.open();

            reportSubSystem.createLogo(document, strategy.getLogoName());
            reportSubSystem.createHeader(document, strategy.getHeaderContent(params));
            reportSubSystem.createContent(document, strategy.getReportContent(params));

            document.close();

            return stream.toByteArray();
        } catch (DocumentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}