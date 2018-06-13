package uk.gov.hmcts.reform.sandl.snlrules.rules.queries;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.model.BookableJudge;
import uk.gov.hmcts.reform.sandl.snlrules.model.BookableRoom;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.DateTimeHelper.offsetDateTimeOf;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.assertResults;

public class QueriesTests {
    private static final String rulesDefinition = "Sessions";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void testJudgeQuery() {

        rules.insert(new Room("room1", "Room A"));
        rules.insert(new Judge("judge1", "John Harris"));

        rules.insert(new Room("room2", "Room B"));
        rules.insert(new Judge("judge2", "John Doe"));

        rules.insert(new Availability("1", "judge1", null,
            OffsetDateTime.of(2018, 3, 5, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("2", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Session("15", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 10, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(15), "FTRACK"));
        rules.insert(new Session("16", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 11, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(30), "FTRACK"));

        rules.insert(new Session("17", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 11, 42, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(10), "FTRACK"));


        rules.fireAllRules();


        OffsetDateTime from = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        Duration dur = Duration.ofMinutes(10);
        for (Object a : rules.getObjects(new ClassObjectFilter(BookableJudge.class))) {
            System.out.println(a.toString());
        }

        QueryResults results = rules.getQueryResults("all bookable judges", from, to);

        for (QueryResultsRow row : results) {
            BookableJudge session = (BookableJudge) row.get("$bookableJudge");
            System.out.println(session.toString());
        }
//        start=2018-03-05T09:00Z, duration=PT3H)
//        start=2018-04-10T09:00Z, duration=PT1H)
//        start=2018-04-10T11:52Z, duration=PT8M)
//        start=2018-04-10T11:30Z, duration=PT12M)
//        start=2018-04-10T10:15Z, duration=PT45M)
    }

    @Test
    public void testRoomQuery() {

        rules.insert(new Room("room1", "Room A"));

        rules.insert(new Room("room2", "Room B"));

        rules.insert(new Availability("1", null, "room1",
            OffsetDateTime.of(2018, 3, 5, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("2", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("2", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Session("15", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 10, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(15), "FTRACK"));
        rules.insert(new Session("16", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 11, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(30), "FTRACK"));

        rules.insert(new Session("17", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 11, 42, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(10), "FTRACK"));

        rules.fireAllRules();


        OffsetDateTime from = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        Duration dur = Duration.ofMinutes(10);
        for (Object a : rules.getObjects(new ClassObjectFilter(BookableRoom.class))) {
            System.out.println(a.toString());
        }

        QueryResults results = rules.getQueryResults("all bookable rooms", from, to);

        for (QueryResultsRow row : results) {
            BookableRoom session = (BookableRoom) row.get("$bookableRoom");
            System.out.println(session.toString());
        }
//        start=2018-03-05T09:00Z, duration=PT3H)
//        start=2018-04-10T09:00Z, duration=PT1H)
//        start=2018-04-10T11:52Z, duration=PT8M)
//        start=2018-04-10T11:30Z, duration=PT12M)
//        start=2018-04-10T10:15Z, duration=PT45M)
    }

    @Test
    public void testRoomAllDayAvailableAndJudgeSomeSlotsQuery() {
        rules.insert(new Room("room1", "Room A"));
        rules.insert(new Judge("judge1", "John Harris"));

        rules.insert(new Room("room2", "Room B"));
        rules.insert(new Judge("judge2", "John Doe"));

        rules.insert(new Availability("1", "judge1", null,
            offsetDateTimeOf("05-03-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Availability("2", "judge1", null,
            offsetDateTimeOf("10-04-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Availability("12", null, "room1",
            offsetDateTimeOf("10-04-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Availability("13", null, "room1",
            offsetDateTimeOf("10-04-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Session("15", null, "room1",
            offsetDateTimeOf("10-04-2018 10:00"),
            Duration.ofMinutes(15), "FTRACK"));
        rules.insert(new Session("16", null, "room1",
            offsetDateTimeOf("10-04-2018 11:00"),
            Duration.ofMinutes(30), "FTRACK"));

        rules.insert(new Session("17", null, "room1",
            offsetDateTimeOf("10-04-2018 11:42"),
            Duration.ofMinutes(10), "FTRACK"));

        rules.fireAllRules();


        OffsetDateTime from = offsetDateTimeOf("09-04-2018 09:00");
        OffsetDateTime to = offsetDateTimeOf("11-04-2018 09:00");

        Duration dur = Duration.ofMinutes(4);

        QueryResults results = rules.getQueryResults("JudgeAndRoomAvailable",
            "judge1", "room1", dur, from, to);

        System.out.println("=========== " + results.size());

        Map<OffsetDateTime, OffsetDateTime> expectedResults = new HashMap<>();
        expectedResults.put(offsetDateTimeOf("10-04-2018 09:00"), offsetDateTimeOf("10-04-2018 10:00"));
        expectedResults.put(offsetDateTimeOf("10-04-2018 10:15"), offsetDateTimeOf("10-04-2018 11:00"));
        expectedResults.put(offsetDateTimeOf("10-04-2018 11:30"), offsetDateTimeOf("10-04-2018 11:42"));
        expectedResults.put(offsetDateTimeOf("10-04-2018 11:52"), offsetDateTimeOf("10-04-2018 12:00"));

        for (QueryResultsRow row : results) {
            OffsetDateTime bookableStart = (OffsetDateTime) row.get("$bookableStart");
            OffsetDateTime bookableEnd = (OffsetDateTime) row.get("$bookableEnd");

            BookableJudge bj = (BookableJudge) row.get("$jb");
            System.out.println(bj.toString());
            BookableRoom rb = (BookableRoom) row.get("$rb");
            System.out.println(rb.toString());
            System.out.println("Possible space for session: " + bookableStart + " - " + bookableEnd + " - " + Duration.between(bookableStart, bookableEnd));
            System.out.println("===========");

            assertResults(expectedResults, bookableStart, bookableEnd);
        }

        assertThat(results.size()).isEqualTo(4);
    }

    @Test
    public void testRoomSomeSlotsAndJudgeSomeSlotsQuery() {

        rules.insert(new Room("room1", "Room A"));
        rules.insert(new Judge("judge1", "John Harris"));

        rules.insert(new Room("room2", "Room B"));
        rules.insert(new Judge("judge2", "John Doe"));

        rules.insert(new Availability("1", "judge1", null,
            offsetDateTimeOf("05-03-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Availability("2", "judge1", null,
            offsetDateTimeOf("10-04-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Availability("12", null, "room1",
            offsetDateTimeOf("10-04-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Availability("13", null, "room1",
            offsetDateTimeOf("10-04-2018 09:00"),
            Duration.ofHours(3)));

        rules.insert(new Session("15", null, "room1",
            offsetDateTimeOf("10-04-2018 10:00"),
            Duration.ofMinutes(15), "FTRACK"));
        rules.insert(new Session("16", null, "room1",
            offsetDateTimeOf("10-04-2018 11:00"),
            Duration.ofMinutes(30), "FTRACK"));

        rules.insert(new Session("17", null, "room1",
            offsetDateTimeOf("10-04-2018 11:42"),
            Duration.ofMinutes(10), "FTRACK"));

        rules.insert(new Session("37", "judge1", null,
            offsetDateTimeOf("10-04-2018 11:39"),
            Duration.ofMinutes(16), "FTRACK"));

        rules.fireAllRules();

        OffsetDateTime from = offsetDateTimeOf("09-04-2018 09:00");
        OffsetDateTime to = offsetDateTimeOf("11-04-2018 09:00");

        Duration dur = Duration.ofMinutes(4);

        Map<OffsetDateTime, OffsetDateTime> expectedResults = new HashMap<>();
        expectedResults.put(offsetDateTimeOf("10-04-2018 10:15"), offsetDateTimeOf("10-04-2018 11:00"));
        expectedResults.put(offsetDateTimeOf("10-04-2018 11:30"), offsetDateTimeOf("10-04-2018 11:39"));
        expectedResults.put(offsetDateTimeOf("10-04-2018 09:00"), offsetDateTimeOf("10-04-2018 10:00"));
        expectedResults.put(offsetDateTimeOf("10-04-2018 11:55"), offsetDateTimeOf("10-04-2018 12:00"));

        QueryResults results = rules.getQueryResults("JudgeAndRoomAvailable",
            "judge1", "room1", dur, from, to);

        System.out.println("=========== " + results.size());

        for (QueryResultsRow row : results) {
            OffsetDateTime bookableStart = (OffsetDateTime) row.get("$bookableStart");
            OffsetDateTime bookableEnd = (OffsetDateTime) row.get("$bookableEnd");

            BookableJudge bj = (BookableJudge) row.get("$jb");
            System.out.println(bj.toString());
            BookableRoom rb = (BookableRoom) row.get("$rb");
            System.out.println(rb.toString());
            System.out.println("Possible space for session: " + bookableStart + " - " + bookableEnd + " - " + Duration.between(bookableStart, bookableEnd));
            System.out.println("===========");

            assertResults(expectedResults, bookableStart, bookableEnd);
        }

        assertThat(results.size()).isEqualTo(4);
    }
}
