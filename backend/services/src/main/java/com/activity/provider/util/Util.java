package com.activity.provider.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final String DATE_TIME_MASK = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_MASK = "dd/MM/yyyy";

    public static LocalDateTime today() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static String convertLocalDateTimeToStringFormatted(ZonedDateTime dateTime, String mask) {
        return DateTimeFormatter.ofPattern(mask).format(dateTime);
    }
}
