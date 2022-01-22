package com.activity.provider.report.strategy;

import com.activity.provider.report.ReportParams;
import com.activity.provider.report.system.ReportContent;
import com.activity.provider.report.system.ReportHeaderField;

import java.util.List;

public interface ReportStrategy {
    String getLogoName();
    List<ReportHeaderField> getHeaderContent(ReportParams params);
    ReportContent getReportContent(ReportParams params);
}