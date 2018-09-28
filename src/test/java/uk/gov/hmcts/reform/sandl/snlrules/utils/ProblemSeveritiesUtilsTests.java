package uk.gov.hmcts.reform.sandl.snlrules.utils;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.exception.MissingResourceProblemSeverityRangeException;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemSeverities;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RunWith(MockitoJUnitRunner.class)
public class ProblemSeveritiesUtilsTests {

    @BeforeClass
    public static void setup() {
        DateTimeUtils.setZone(ZoneOffset.UTC.normalized());
    }

    @Test
    public void calculateForMissingResource_returns_WarningForLessThen29to15daysOfStartToCurrentDate() {
        OffsetDateTime startDate =
            OffsetDateTime.of(2018, 5, 29, 9, 0, 0, 0, ZoneOffset.UTC);
        ProblemSeverities actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate, 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Warning, actualSeverity);
        actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate.withDayOfMonth(16), 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Warning, actualSeverity);
    }

    @Test
    public void calculateForMissingResource_returns_UrgentForLessThen15to2daysOfStartToCurrentDate() {
        OffsetDateTime startDate =
            OffsetDateTime.of(2018, 5, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ProblemSeverities actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate, 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Urgent, actualSeverity);

        actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate.withDayOfMonth(3), 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Urgent, actualSeverity);
    }

    @Test
    public void calculateForMissingResource_returns_CriticalForLessThen2to0daysOfStartToCurrentDate() {
        OffsetDateTime startDate =
            OffsetDateTime.of(2018, 5, 2, 9, 0, 0, 0, ZoneOffset.UTC);
        ProblemSeverities actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate, 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Critical, actualSeverity);

        actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate.withDayOfMonth(1), 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Critical, actualSeverity);
    }

    @Test(expected = MissingResourceProblemSeverityRangeException.class)
    public void calculateForMissingResource_throwsError_whenNoConfigForGivenDate() {
        OffsetDateTime startDate =
            OffsetDateTime.of(2018, 5, 30, 9, 0, 0, 0, ZoneOffset.UTC);
        ProblemSeverities actualSeverity =
            ProblemSeveritiesUtils.calculateForMissingResource(startDate, 2018, 5, 1);

        Assert.assertEquals(ProblemSeverities.Critical, actualSeverity);
    }

}
