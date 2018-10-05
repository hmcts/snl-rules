package uk.gov.hmcts.reform.sandl.snlrules.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.gov.hmcts.reform.sandl.snlrules.exception.MissingResourceProblemSeverityRangeException;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemSeverities;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

public class ProblemSeveritiesUtils {
    private static List<MissingResourceProblemSeverityEntry> missingResourceSeverityRules = Arrays.asList(
        new MissingResourceProblemSeverityEntry(15, 29, ProblemSeverities.Warning),
        new MissingResourceProblemSeverityEntry(2, 15, ProblemSeverities.Urgent),
        new MissingResourceProblemSeverityEntry(0, 2, ProblemSeverities.Critical)
    );

    private ProblemSeveritiesUtils() {
    }

    public static ProblemSeverities calculateForMissingResource(
        OffsetDateTime start, int currentYear, int currentMonth, int currentDay) {

        long days = DateTimeUtils.calculateDaysBetweenDates(start, currentYear, currentMonth, currentDay);

        return missingResourceSeverityRules
            .stream()
            .filter(entry -> entry.minDaysIncluding <= days && days < entry.maxDaysExcluding)
            .findFirst()
            .orElseThrow(() ->
                new MissingResourceProblemSeverityRangeException(days, start, currentYear, currentMonth, currentDay)
            )
            .getSeverity();
    }

    @Data
    @AllArgsConstructor
    static class MissingResourceProblemSeverityEntry {
        private int minDaysIncluding;
        private int maxDaysExcluding;
        private ProblemSeverities severity;
    }
}
