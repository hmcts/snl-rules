package uk.gov.hmcts.reform.sandl.snlrules.utils;

import org.apache.commons.lang3.time.DurationFormatUtils;
import uk.gov.hmcts.reform.sandl.snlrules.exception.DateComparisonException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateTimeUtils {

    public static ZoneId zone = ZoneOffset.systemDefault();

    private DateTimeUtils() {
    }

    public static boolean between(OffsetDateTime dateTimeToCheck,
                                  int currentYear, int currentMonth, int currentDay,
                                  int greaterOrEqualsDays, int lessDays) {
        OffsetDateTime dateToCheck = dateTimeToCheck.truncatedTo(ChronoUnit.DAYS);

        OffsetDateTime currentDate = OffsetDateTime.of(
            currentYear, currentMonth, currentDay,
            0, 0, 0, 0, ZoneOffset.UTC);

        long days = ChronoUnit.DAYS.between(currentDate, dateToCheck);

        return greaterOrEqualsDays <= days && days < lessDays;
    }

    public static boolean between(OffsetDateTime dateTimeToCheck, OffsetDateTime start, OffsetDateTime end) {
        OffsetDateTime daysStart = start.truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime daysEnd = end.truncatedTo(ChronoUnit.DAYS);

        if (daysStart.isAfter(daysEnd)) {
            throw new DateComparisonException("Start date cannot be after the end date.");
        }

        OffsetDateTime daysToCheck = dateTimeToCheck.truncatedTo(ChronoUnit.DAYS);
        return !daysStart.isAfter(daysToCheck) && !daysEnd.isBefore(daysToCheck);
    }

    public static boolean olderThan(OffsetDateTime dateTimeToCheck,
                                    int currentYear, int currentMonth, int currentDay,
                                    int olderThan) {
        if (dateTimeToCheck == null) {
            throw new IllegalArgumentException("Date time to check cannot be null");
        }

        OffsetDateTime dateToCheck = dateTimeToCheck.truncatedTo(ChronoUnit.DAYS);

        OffsetDateTime currentDate = OffsetDateTime.of(
            currentYear, currentMonth, currentDay,
            0, 0, 0, 0, ZoneOffset.UTC);

        long days = ChronoUnit.DAYS.between(dateToCheck, currentDate);

        return olderThan < days;
    }

    public static boolean isAfter(OffsetDateTime date, int currentYear, int currentMonth, int currentDay) {
        OffsetDateTime currentDate = OffsetDateTime.of(currentYear, currentMonth, currentDay,
            0, 0, 0, 0, ZoneOffset.UTC);

        return date.isAfter(currentDate);
    }

    public static String humanizeDate(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return "N/A";
        }
        return dateTime
            .atZoneSameInstant(DateTimeUtils.zone)
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public static boolean contains(OffsetDateTime biggerStart, OffsetDateTime biggerEnd,
                                   OffsetDateTime smallerOrEqualStart, OffsetDateTime smallerOrEqualEnd) {

        boolean startsAfterOrEqual = !smallerOrEqualStart.isBefore(biggerStart);
        boolean endsBeforeOrEqual = !smallerOrEqualEnd.isAfter(biggerEnd);
        return startsAfterOrEqual && endsBeforeOrEqual;
    }

    public static OffsetDateTime max(OffsetDateTime v1, OffsetDateTime v2, OffsetDateTime v3) {
        OffsetDateTime win1 = v1.isAfter(v2) ? v1 : v2;
        return v3.isAfter(win1) ? v3 : win1;
    }

    public static OffsetDateTime min(OffsetDateTime v1, OffsetDateTime v2, OffsetDateTime v3) {
        OffsetDateTime win1 = v1.isAfter(v2) ? v2 : v1;
        return v3.isAfter(win1) ? win1 : v3;
    }

    public static OffsetDateTime offsetDateTimeOf(String date) {
        String dateFormat = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);

        return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
    }

    public static boolean isGreaterOrEquals(OffsetDateTime v1, OffsetDateTime v2) {
        return !v1.isBefore(v2);
    }

    public static boolean isLessOrEquals(OffsetDateTime v1, OffsetDateTime v2) {
        return !v1.isAfter(v2);
    }

    /**
     * Returns time duration in a format: HH:mm ex: Duration of '1hour 5minutes' gives '01:05'.
     *
     * @param duration - object to transform
     * @return transformed duration in HH:mm format
     */
    public static String readableDuration(Duration duration) {
        return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm");
    }
}
