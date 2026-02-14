package com.web.site.global.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CustomDateFormat {
    private static final DateTimeFormatter NOWDATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter EXCELFORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    public static String nowDateTime() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                .truncatedTo(ChronoUnit.SECONDS);
        return now.format(NOWDATEFORMATTER);
    }

    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(EXCELFORMATTER);
    }

    public static String formatDateTime(LocalDateTime time) {
        return time.format(NOWDATEFORMATTER);
    }
}
