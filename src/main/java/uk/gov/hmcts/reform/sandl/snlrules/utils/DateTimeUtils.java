package uk.gov.hmcts.reform.sandl.snlrules.utils;

import uk.gov.hmcts.reform.sandl.snlrules.exception.DateComparisonException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateTimeUtils {
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
        dateTimeToCheck = dateTimeToCheck.truncatedTo(ChronoUnit.DAYS);
        start = start.truncatedTo(ChronoUnit.DAYS);
        end = end.truncatedTo(ChronoUnit.DAYS);

        if (start.isAfter(end)) {
            throw new DateComparisonException("Start date cannot be after the end date.");
        }

        return !start.isAfter(dateTimeToCheck) && !end.isBefore(dateTimeToCheck);
    }

    public static boolean olderThan(OffsetDateTime dateTimeToCheck,
                                    int currentYear, int currentMonth, int currentDay,
                                    int olderThan) {
        if (dateTimeToCheck == null) {
            throw new NullPointerException("Date time to check cannot be null");
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
        return dateTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public static boolean sameDay(OffsetDateTime one, OffsetDateTime two) {
        OffsetDateTime startTruncated = one.truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime toCompareStartTruncated = two.truncatedTo(ChronoUnit.DAYS);

        return !startTruncated.isAfter(toCompareStartTruncated) && !startTruncated.isBefore(toCompareStartTruncated);
    }
}
