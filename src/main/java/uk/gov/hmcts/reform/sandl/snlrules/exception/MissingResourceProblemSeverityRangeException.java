package uk.gov.hmcts.reform.sandl.snlrules.exception;

import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.time.OffsetDateTime;

public class MissingResourceProblemSeverityRangeException extends RuntimeException {
    public MissingResourceProblemSeverityRangeException(
        long days, OffsetDateTime start, int currentYear, int currentMonth, int currentDay) {
        super(MissingResourceProblemSeverityRangeException.createMessage(
            days, start, currentYear, currentMonth, currentDay)
        );
    }

    private static String createMessage(
        long days, OffsetDateTime start, int currentYear, int currentMonth, int currentDay) {

        return String.format("Missing a Range for Problem Severities calculation, days: %d, start: %s, current: %s",
            days, DateTimeUtils.humanizeDate(start),
            DateTimeUtils.humanizeDate(
                OffsetDateTime.of(currentYear, currentMonth, currentDay, 0, 0, 0, 0, start.getOffset())
            ));
    }
}
