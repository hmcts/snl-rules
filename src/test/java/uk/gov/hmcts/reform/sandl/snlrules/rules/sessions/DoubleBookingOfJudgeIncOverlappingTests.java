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

@RunWith(MockitoJUnitRunner.class)
public class DoubleBookingOfJudgeIncOverlappingTests {

    private final static String rulesDefinition = "Sessions";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_judge_is_not_double_booked() {
        String sessionId_1 = "08db06c5-2457-4501-bd83-15aa5037f930";
        String sessionId_2 = "dcc87520-75f0-4427-9810-d851485dc1a7";
        String roomId_1 = "33362d68-76e3-430a-84c4-23983c448dc2";
        String roomId_2 = "13922372-5db8-4268-8ffc-f7288f233b92";
        String judgeId = "9837c832-25e2-4631-99e8-b6a1d7ba9fe2";

        rules.insert(new Session(sessionId_1, judgeId, roomId_1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId_2, judgeId, roomId_2,
            OffsetDateTime.of(2018, 4, 10, 10, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Double booking of judge (includes any overlapping)"));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_same_time_but_different_judge() {
        String sessionId_1 = "08db06c5-2457-4501-bd83-15aa5037f930";
        String sessionId_2 = "dcc87520-75f0-4427-9810-d851485dc1a7";
        String roomId_1 = "33362d68-76e3-430a-84c4-23983c448dc2";
        String roomId_2 = "13922372-5db8-4268-8ffc-f7288f233b92";
        String judgeId_1 = "9837c832-25e2-4631-99e8-b6a1d7ba9fe2";
        String judgeId_2 = "340968cb-b38c-42e5-8867-e8d06bf08ada";

        rules.insert(new Session(sessionId_1, judgeId_1, roomId_1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId_2, judgeId_2, roomId_2,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Double booking of judge (includes any overlapping)"));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_judge_is_double_booked_same_time() {
        String sessionId_1 = "08db06c5-2457-4501-bd83-15aa5037f930";
        String sessionId_2 = "dcc87520-75f0-4427-9810-d851485dc1a7";
        String roomId_1 = "33362d68-76e3-430a-84c4-23983c448dc2";
        String roomId_2 = "13922372-5db8-4268-8ffc-f7288f233b92";
        String judgeId = "9837c832-25e2-4631-99e8-b6a1d7ba9fe2";

        rules.insert(new Session(sessionId_1, judgeId, roomId_1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId_2, judgeId, roomId_2,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Double booking of judge (includes any overlapping)"));

        assertProblems(droolsService,1, 0, 0);
    }

    @Test
    public void should_be_problem_when_judge_is_double_booked_sooner_and_overlapping() {
        String sessionId_1 = "08db06c5-2457-4501-bd83-15aa5037f930";
        String sessionId_2 = "dcc87520-75f0-4427-9810-d851485dc1a7";
        String roomId_1 = "33362d68-76e3-430a-84c4-23983c448dc2";
        String roomId_2 = "13922372-5db8-4268-8ffc-f7288f233b92";
        String judgeId = "9837c832-25e2-4631-99e8-b6a1d7ba9fe2";

        rules.insert(new Session(sessionId_1, judgeId, roomId_1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId_2, judgeId, roomId_2,
            OffsetDateTime.of(2018, 4, 10, 8, 30, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Double booking of judge (includes any overlapping)"));

        assertProblems(droolsService,1, 0, 0);
    }

    @Test
    public void should_be_problem_when_judge_is_double_booked_later_and_overlapping() {
        String sessionId_1 = "08db06c5-2457-4501-bd83-15aa5037f930";
        String sessionId_2 = "dcc87520-75f0-4427-9810-d851485dc1a7";
        String roomId_1 = "33362d68-76e3-430a-84c4-23983c448dc2";
        String roomId_2 = "13922372-5db8-4268-8ffc-f7288f233b92";
        String judgeId = "9837c832-25e2-4631-99e8-b6a1d7ba9fe2";

        rules.insert(new Session(sessionId_1, judgeId, roomId_1,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new Session(sessionId_2, judgeId, roomId_2,
            OffsetDateTime.of(2018, 4, 10, 9, 30, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Double booking of judge (includes any overlapping)"));

        assertProblems(droolsService,1, 0, 0);
    }
}
