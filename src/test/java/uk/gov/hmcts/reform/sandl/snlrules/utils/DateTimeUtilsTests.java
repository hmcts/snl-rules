package uk.gov.hmcts.reform.sandl.snlrules.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.exception.DateComparisonException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

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

    @Test
    public void should_be_between_two_dates() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime after = OffsetDateTime.of(2018, 05, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime before = OffsetDateTime.of(2018, 05, 21, 9, 0, 0, 0, ZoneOffset.UTC);

        Assert.assertTrue(DateTimeUtils.between(dateToBeChecked, after, before));
    }

    @Test
    public void should_not_be_between_two_dates() {
        OffsetDateTime dateToBeChecked = OffsetDateTime.of(2018, 05, 30, 9, 0, 0, 0, ZoneOffset.UTC);
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

//TODO testy z max
}
