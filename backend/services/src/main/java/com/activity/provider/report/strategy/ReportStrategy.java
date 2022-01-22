package com.activity.provider.report.strategy;

import com.activity.provider.report.ReportParams;
import com.activity.provider.report.system.ReportSubSystemRepresentation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ReportStrategy {
    String getLogo();
    void createHeader(ReportSubSystemRepresentation reportSubSystem, Document document, ReportParams params) throws DocumentException, IOException, URISyntaxException;
    void createContent(ReportSubSystemRepresentation reportSubSystem, Document document, ReportParams params) throws DocumentException, IOException, URISyntaxException;
}