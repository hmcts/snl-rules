package uk.gov.hmcts.reform.sandl.snlrules.models;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(SpringRunner.class)
public class AvailabilityTests {

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
        Availability testObject =
            new Availability("Id", "jId", "rId", OffsetDateTime.parse("2018-09-26T13:00:00.000Z"),
                Duration.ofHours(1).plusMinutes(2));

        String expected = "Start: 26/09/2018 16:00, duration: 01:02";

        assertThat(testObject.toDescription()).isEqualTo(expected);
    }

    @Test
    public void toDescription_returnsProperlyFormattedString_withOneFieldNull() {
        Availability testObject =
            new Availability("Id", "jId", "rId", null,
                Duration.ofHours(1).plusMinutes(2));

        String expected = "Start: N/A, duration: 01:02";

        assertThat(testObject.toDescription()).isEqualTo(expected);
    }

}
