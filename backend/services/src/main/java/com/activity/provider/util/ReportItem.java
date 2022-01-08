package com.activity.provider.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itextpdf.text.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportItem {
    private String value;
    private int alignment;

    public static ReportItem build(String value, int alignment) {
        return ReportItem.builder().value(value).alignment(alignment).build();
    }
}