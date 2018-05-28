package uk.gov.hmcts.reform.sandl.snlrules.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
}
