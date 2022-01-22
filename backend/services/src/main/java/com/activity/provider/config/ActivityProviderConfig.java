package com.activity.provider.config;

import com.activity.provider.report.strategy.ActivityPerformanceReportStrategy;
import com.activity.provider.report.strategy.ReportStrategy;
import com.activity.provider.report.strategy.ReportType;
import com.activity.provider.report.strategy.StudentPerformanceReportStrategy;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class ActivityProviderConfig {

    private StudentPerformanceReportStrategy studentPerformanceReportStrategy;
    private ActivityPerformanceReportStrategy activityPerformanceReportStrategy;

    @Bean
    public Map<ReportType, ReportStrategy> reportStrategies() {
        Map<ReportType, ReportStrategy> reportStrategies = new HashMap<>();

        reportStrategies.put(ReportType.STUDENT_PERFORMANCE, studentPerformanceReportStrategy);
        reportStrategies.put(ReportType.ACTIVITY_PERFORMANCE, activityPerformanceReportStrategy);

        return reportStrategies;
    }
}
