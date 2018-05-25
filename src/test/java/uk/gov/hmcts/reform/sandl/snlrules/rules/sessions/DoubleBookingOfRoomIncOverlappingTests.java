package uk.gov.hmcts.reform.sandl.snlrules.rules.sessions;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.assertProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class DoubleBookingOfRoomIncOverlappingTests {

    private static final String rulesDefinition = "Sessions";

    private static final String DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING
        = "Double booking of room (includes any overlapping)";

    private static final String sessionId1 = "08db06c5-2457-4501-bd83-15aa5037f930";
    private static final String sessionId2 = "dcc87520-75f0-4427-9810-d851485dc1a7";
    private static final String roomId1 = "33362d68-76e3-430a-84c4-23983c448dc2";
    private static final String roomId2 = "b34dd2fc-67b8-4b15-ab17-0a39692b59e2";
    private static final String judgeId1 = "9837c832-25e2-4631-99e8-b6a1d7ba9fe2";
    private static final String judgeId2 = "bff7f85b-6c75-4f10-b75d-45e357a612c2";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_room_is_not_double_booked() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId1, judgeId1, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId2, judgeId2, roomId1,
            OffsetDateTime.of(2018, 4, 10, 10, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_same_time_but_different_room() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId1, judgeId1, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId2, judgeId2, roomId2,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_same_time_and_room_but_more_than_2_weeks_before() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId1, judgeId1, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId2, judgeId2, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_room_is_double_booked_same_time_and_less_than_2_weeks_before() {
        setDateInRules(rules, 2018, 4, 5);

        rules.insert(new Session(sessionId1, judgeId1, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId2, judgeId2, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING));

        assertProblems(droolsService,1, 0, 0);
    }

    @Test
    public void should_be_problem_when_judge_is_double_booked_sooner_and_overlapping_and_less_than_2_weeks_before() {
        setDateInRules(rules, 2018, 04, 5);

        rules.insert(new Session(sessionId1, judgeId1, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId2, judgeId2, roomId1,
            OffsetDateTime.of(2018, 4, 10, 8, 30, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING));

        assertProblems(droolsService,1, 0, 0);
    }

    @Test
    public void should_be_problem_when_judge_is_double_booked_later_and_overlapping_and_less_than_2_weeks_before() {
        setDateInRules(rules, 2018, 04, 5);

        rules.insert(new Session(sessionId1, judgeId1, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId2, judgeId2, roomId1,
            OffsetDateTime.of(2018, 4, 10, 9, 30, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(DOUBLE_BOOKING_OF_ROOM_INCLUDES_ANY_OVERLAPPING));

        assertProblems(droolsService,1, 0, 0);
    }
}
