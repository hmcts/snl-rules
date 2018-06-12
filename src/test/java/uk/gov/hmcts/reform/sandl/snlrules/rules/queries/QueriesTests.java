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
import uk.gov.hmcts.reform.sandl.snlrules.model.SearchSession;
import uk.gov.hmcts.reform.sandl.snlrules.model.SearchSessionResult;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    }

    @Test
    public void testRoomAllDayAvailableAndJudgeSomeSlotsQuery() {

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

        rules.insert(new Availability("12", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("13", null, "room1",
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


        OffsetDateTime from = OffsetDateTime.of(2018, 4, 9, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.of(2018, 4, 11, 9, 0, 0, 0, ZoneOffset.UTC);
        Duration dur = Duration.ofMinutes(4);

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
        }

        assertThat(results.size()).isEqualTo(4);
        //TODO more asserts
        // expected results
//        09:00-10:00 dur 60m
//        10:15-11:00 dur 45m
//        11:30-11:42 dur 12m
//        11:52-12:00 dur 8m
    }

    @Test
    public void testRoomSomeSlotsAndJudgeSomeSlotsQuery() {

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

        rules.insert(new Availability("12", null, "room1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("13", null, "room1",
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

        rules.insert(new Session("37", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 11, 39, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(16), "FTRACK"));

        rules.fireAllRules();


        OffsetDateTime from = OffsetDateTime.of(2018, 4, 9, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.of(2018, 4, 11, 9, 0, 0, 0, ZoneOffset.UTC);
        Duration dur = Duration.ofMinutes(4);

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
        }

        assertThat(results.size()).isEqualTo(4);
// assert
        // 2018-04-10T10:15Z - 2018-04-10T11:00Z - PT45M
        //2018-04-10T11:30Z - 2018-04-10T11:39Z - PT9M
        //2018-04-10T09:00Z - 2018-04-10T10:00Z - PT1H
        //2018-04-10T11:55Z - 2018-04-10T12:00Z - PT5M
    }
}
