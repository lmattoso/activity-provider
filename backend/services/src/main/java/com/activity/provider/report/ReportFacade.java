package com.activity.provider.report;

import com.activity.provider.report.strategy.ReportStrategy;
import com.activity.provider.report.strategy.ReportType;
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
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class ReportFacade {

    private ReportSubSystemRepresentation reportSubSystem;

    private Map<ReportType, ReportStrategy> reportStrategies;

    public byte[] generateReport(ReportParams params) {
        ReportStrategy strategy = this.getStrategy(params.getReportType());

        try {
            Document document = new Document();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, stream);

            document.open();

            reportSubSystem.createLogo(document, strategy.getLogo());

            strategy.createHeader(reportSubSystem, document, params);
            strategy.createContent(reportSubSystem, document, params);

            document.close();

            return stream.toByteArray();
        } catch (DocumentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ReportStrategy getStrategy(ReportType type) {
        return Optional.ofNullable(reportStrategies.get(type)).orElseThrow();
    }
}