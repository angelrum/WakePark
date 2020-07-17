package ru.project.wakepark.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final String TIME_PATTERN = "HH:mm";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    private static final LocalDateTime MIN_DATE_TIME = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE_TIME = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static LocalDate atStartOfDayOrMin(LocalDate localDate) {
        return localDate != null ? localDate : MIN_DATE;
    }
    //localDate.plus(1, ChronoUnit.DAYS).atStartOfDay()
    public static LocalDate atStartOfNextDayOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plusDays(1) : MAX_DATE;
    }

    public static LocalDateTime atStartOfDayTimeOrMin(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE_TIME;
    }

    public static LocalDateTime atStartOfNextDayTimeOrMax(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE_TIME;
    }

    private DateTimeUtil() {
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static @Nullable
    LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static @Nullable
    LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }
}
