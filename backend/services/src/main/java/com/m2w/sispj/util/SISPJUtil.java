package com.m2w.sispj.util;

import com.m2w.sispj.domain.User;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SISPJUtil {
    public static final String DATE_TIME_MASK = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_MASK = "dd/MM/yyyy";

    public static LocalDateTime today() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static Long todayLong() {
        return LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static String convertLocalDateTimeToStringFormatted(ZonedDateTime dateTime, String mask) {
        return DateTimeFormatter.ofPattern(mask).format(dateTime);
    }

    public static ZonedDateTime convertStringFormattedToLocalDateTimeTo(String value, String mask) {
        return ZonedDateTime.parse(value, DateTimeFormatter.ofPattern(mask));
    }

    public static User getCurrentUser() {
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String formatCurrency(BigDecimal amount, String languageCode, String countryCode) {
        if(amount != null) {
            Locale locale = new Locale(languageCode, countryCode);
            if (!LocaleUtils.isAvailableLocale(locale)) {
                locale = new Locale("en", "US");
            }
            Format format = NumberFormat.getCurrencyInstance(locale);

            return format.format(amount);
        }

        return "";
    }
}
