package uk.gov.hmcts.reform.sandl.snlrules.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.exception.DateComparisonException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils.offsetDateTimeOf;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeUtilsTests {
    @Test
    public void should_be_between_when_in_the_middle_of_period() {
        Assert.assertTrue(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 7,
            0, 10
        ));
    }

    @Test
    public void should_be_between_when_on_greater_or_equals_0() {
        Assert.assertTrue(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 10,
            0, 10
        ));
    }

    @Test
    public void should_be_between_when_less_than_date_minus_1_from_less() {
        Assert.assertTrue(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 17, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 8,
            0, 10
        ));
    }

    @Test
    public void should_be_not_between_when_date_equals_less() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 17, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 7,
            0, 10
        ));
    }

    @Test
    public void should_be_not_between_when_after_the_date() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 17, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 18,
            0, 10
        ));
    }

    @Test
    public void should_be_not_between_when_date_minus_1_from_greater_or_equals() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 8,
            3, 10
        ));
    }

    @Test
    public void should_be_not_between_when_far_before_greater_or_equals() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 04, 7,
            0, 10
        ));
    }

    @Test
    public void should_be_not_between_when_far_after_less() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 06, 7,
            0, 10
        ));
    }

    @Test
    public void should_be_between_when_4_and_7() {
        Assert.assertTrue(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 4,
            4, 7
        ));
    }

    @Test
    public void should_not_be_between_when_4_and_7_on_last_day() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 3,
            4, 7
        ));
    }

    @Test
    public void should_not_be_between_when_4_and_7() {
        Assert.assertFalse(DateTimeUtils.between(
            OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            2018, 05, 9,
            4, 7
        ));
    }

    @Test
    public void should_be_older_than_10_days() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        Assert.assertTrue(DateTimeUtils.olderThan(
            dateToBeChecked, 2018, 05, 21, 10));
    }

    @Test
    public void should_not_be_older_than_10_days() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        Assert.assertFalse(DateTimeUtils.olderThan(
            dateToBeChecked, 2018, 05, 20, 10));
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_date_null() {
        DateTimeUtils.olderThan(
            null, 2018, 05, 20, 10);
    }

    @Test
    public void should_be_between_two_dates() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test
    public void should_not_be_between_two_dates_when_after() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 30, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertFalse(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test
    public void should_not_be_between_two_dates_when_before() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 04, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertFalse(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test
    public void should_be_between_two_dates_when_on_date() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test
    public void should_be_between_two_dates_when_on_date_but_different_hours() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test(expected = DateComparisonException.class)
    public void should_throw_exception_when_after_date_is_actually_greater_than_before_date() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 30, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test
    public void should_contain_other_date_time() {
        OffsetDateTime biggerStart = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime biggerEnd = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualStart = OffsetDateTime.of(2018, 05, 21, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualEnd = OffsetDateTime.of(2018, 05, 21, 11, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.contains(biggerStart, biggerEnd, smallerOrEqualStart, smallerOrEqualEnd));
    }

    @Test
    public void should_contain_other_date() {
        OffsetDateTime biggerStart = OffsetDateTime.of(2018, 05, 20, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime biggerEnd = OffsetDateTime.of(2018, 05, 22, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualStart = OffsetDateTime.of(2018, 05, 21, 1, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualEnd = OffsetDateTime.of(2018, 05, 21, 11, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.contains(biggerStart, biggerEnd, smallerOrEqualStart, smallerOrEqualEnd));
    }

    @Test
    public void should_contain_other_date_time_equals() {
        OffsetDateTime biggerStart = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime biggerEnd = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualStart = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualEnd = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.contains(biggerStart, biggerEnd, smallerOrEqualStart, smallerOrEqualEnd));
    }

    @Test
    public void should_not_contain_other_date_time() {
        OffsetDateTime biggerStart = OffsetDateTime.of(2018, 05, 21, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime biggerEnd = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualStart = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime smallerOrEqualEnd = OffsetDateTime.of(2018, 05, 21, 12, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertFalse(DateTimeUtils.contains(biggerStart, biggerEnd, smallerOrEqualStart, smallerOrEqualEnd));
    }

    @Test
    public void should_pick_correct_max_same_day() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-04 12:00");
        OffsetDateTime medium = offsetDateTimeOf("2018-03-04 13:00");
        OffsetDateTime max = offsetDateTimeOf("2018-03-04 15:00");

        Assert.assertEquals(DateTimeUtils.max(min, medium, max), max);
    }

    @Test
    public void should_pick_correct_max_same_day_different_args_order() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-04 12:00");
        OffsetDateTime medium = offsetDateTimeOf("2018-03-04 13:00");
        OffsetDateTime max = offsetDateTimeOf("2018-03-04 15:00");

        Assert.assertEquals(DateTimeUtils.max(max, medium, min), max);
    }

    @Test
    public void should_pick_correct_max_different_days() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-06 13:00");
        OffsetDateTime medium = offsetDateTimeOf("2018-03-07 13:00");
        OffsetDateTime max = offsetDateTimeOf("2018-03-08 13:00");

        Assert.assertEquals(DateTimeUtils.max(min, medium, max), max);
    }

    @Test
    public void should_pick_correct_max_two_same_dates() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-06 13:00");
        OffsetDateTime max1 = offsetDateTimeOf("2018-03-08 13:00");
        OffsetDateTime max2 = offsetDateTimeOf("2018-03-08 13:00");

        Assert.assertEquals(DateTimeUtils.max(min, max1, max2), max2);
    }

    @Test
    public void should_pick_correct_min_same_day() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-04 12:00");
        OffsetDateTime medium = offsetDateTimeOf("2018-03-04 13:00");
        OffsetDateTime max = offsetDateTimeOf("2018-03-04 15:00");

        Assert.assertEquals(DateTimeUtils.min(min, medium, max), min);
    }

    @Test
    public void should_pick_correct_min_same_day_different_args_order() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-04 12:00");
        OffsetDateTime medium = offsetDateTimeOf("2018-03-04 13:00");
        OffsetDateTime max = offsetDateTimeOf("2018-03-04 15:00");

        Assert.assertEquals(DateTimeUtils.min(max, medium, min), min);
    }

    @Test
    public void should_pick_correct_min_different_days() {
        OffsetDateTime min = offsetDateTimeOf("2018-03-06 13:00");
        OffsetDateTime medium = offsetDateTimeOf("2018-03-07 13:00");
        OffsetDateTime max = offsetDateTimeOf("2018-03-08 13:00");

        Assert.assertEquals(DateTimeUtils.min(min, medium, max), min);
    }

    @Test
    public void should_pick_correct_min_two_same_dates() {
        OffsetDateTime max = offsetDateTimeOf("2018-03-10 13:00");
        OffsetDateTime min1 = offsetDateTimeOf("2018-03-08 13:00");
        OffsetDateTime min2 = offsetDateTimeOf("2018-03-08 13:00");

        Assert.assertEquals(DateTimeUtils.min(max, min2, min1), min2);
    }

    @Test
    public void humanizeData_should_returnDateFormattedIn_DateTimeUtils_Zone() {
        DateTimeUtils.setZone(ZoneOffset.ofHoursMinutes(3, 0));

        OffsetDateTime utc = OffsetDateTime.of(LocalDateTime.of(2018, 3, 10, 13, 0, 0, 0),
            ZoneOffset.ofHoursMinutes(0, 0));
        OffsetDateTime minus6Zone = OffsetDateTime.of(LocalDateTime.of(2018, 3, 10, 13, 0, 0, 0),
            ZoneOffset.ofHoursMinutes(-6, 0));
        OffsetDateTime utcParsedString = OffsetDateTime.parse("2018-09-26T12:06:00.000Z");

        Assert.assertEquals("10/03/2018 16:00", DateTimeUtils.humanizeDate(utc));
        Assert.assertEquals("10/03/2018 22:00", DateTimeUtils.humanizeDate(minus6Zone));
        Assert.assertEquals("26/09/2018 15:06", DateTimeUtils.humanizeDate(utcParsedString));

        DateTimeUtils.setZone(ZoneOffset.systemDefault());
    }

    @Test
    public void readableDuration_should_returnProperlyFormattedString() {
        Duration hoursAndMinutes = Duration.ofHours(11).plusMinutes(15);
        Duration onlyMinutes = Duration.ofHours(0).plusMinutes(30);
        Duration onlyHours = Duration.ofHours(1).plusMinutes(0);
        Duration days = Duration.ofHours(131).plusMinutes(15);

        Assert.assertEquals("11:15", DateTimeUtils.readableDuration(hoursAndMinutes));
        Assert.assertEquals("00:30", DateTimeUtils.readableDuration(onlyMinutes));
        Assert.assertEquals("01:00", DateTimeUtils.readableDuration(onlyHours));
        Assert.assertEquals("131:15", DateTimeUtils.readableDuration(days));
    }

}
