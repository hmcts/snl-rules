package uk.gov.hmcts.reform.sandl.snlrules.models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
public class HearingPartTests {

    @BeforeClass
    public static void setup() {
        DateTimeUtils.setZone(ZoneOffset.ofHoursMinutes(3, 0));
    }

    @AfterClass
    public static void teradown() {
        DateTimeUtils.setZone(ZoneOffset.systemDefault());
    }

    @Test
    public void toDescription_returnsProperlyFormattedString_whenAllFieldsAreGiven() {
        HearingPart testObject =
            new HearingPart("id", "sId", "ct", "ht",
                Duration.ofHours(1).plusMinutes(2),
                OffsetDateTime.parse("2018-09-26T13:00:00.000Z"),
                OffsetDateTime.parse("2018-09-26T16:00:00.000Z"));
        testObject.setScheduleStart(OffsetDateTime.parse("2018-09-26T14:00:00.000Z"));

        String expected = "Duration: 01:02, Case type code: ct, Hearing type code: ht,"
            + " Scheduled start: 26/09/2018 17:00, Scheduled end: 26/09/2018 19:00"
            + ", Created at: 2018-09-26T13:00Z";

        assertThat(testObject.toDescription()).isEqualTo(expected);
    }

    @Test
    public void toDescription_returnsProperlyFormattedString_withOneFieldNull() {
        HearingPart testObject =
            new HearingPart("id", "sId", "ct", "ht",
                Duration.ofHours(1).plusMinutes(2),
                OffsetDateTime.parse("2018-09-26T13:00:00.000Z"),
                OffsetDateTime.parse("2018-09-26T16:00:00.000Z"));

        String expected = "Duration: 01:02, Case type code: ct, Hearing type code: ht,"
            + " Scheduled start: N/A, Scheduled end: 26/09/2018 19:00"
            + ", Created at: 2018-09-26T13:00Z";

        assertThat(testObject.toDescription()).isEqualTo(expected);
    }

}
