package uk.gov.hmcts.reform.sandl.snlrules.rules.sessions;

import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.Problem;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemSeverities;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.assertProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.getInsertedProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class SessionDoesNotHaveARoomTests {

    private static final String rulesDefinition = "Sessions";

    private static final String SESSION_DOES_NOT_HAVE_A_ROOM
        = "Session does not have a room";

    private static final String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
    private static final String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_session_does_not_have_a_room_more_than_4_weeks_before_start() {
        setDateInRules(rules, 2018, 2, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_session_does_not_have_a_room_but_is_in_the_past() {
        setDateInRules(rules, 2018, 5, 23);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_does_not_have_a_room_4_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 4, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 29, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));

        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 30);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_room_and_date_changes_to_4_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 29, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 1);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));

        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 30);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_room_and_date_changes_to_2_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 15, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 1);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));

        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 16);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_room_and_date_changes_to_1_day_or_nearer_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 3, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 2);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 4);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_room_and_date_changes_from_15_days_to_14_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 16, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 1);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 2);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 1, 0, 1);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 17);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_room_and_date_changes_from_2_days_to_1_before_start() {
        setDateInRules(rules, 2018, 1, 2);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 4, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 2);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 3);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 1, 0, 1);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 5);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void problemSeverityShouldChangeWhenTimePassesCheckPoints_whenSessionDoesNotHaveAroom() {
        setDateInRules(rules, 2018, 4, 1);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 30, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 2);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        Problem problem = (Problem) RulesTestHelper.getInsertedProblems(droolsService).get(0).getNewFact();
        Assert.assertEquals(ProblemSeverities.Warning, problem.getSeverity());

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 16);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        problem = (Problem) RulesTestHelper.getInsertedProblems(droolsService).get(0).getNewFact();
        Assert.assertEquals(ProblemSeverities.Urgent, problem.getSeverity());

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 29);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM));
        problem = (Problem) RulesTestHelper.getInsertedProblems(droolsService).get(0).getNewFact();
        Assert.assertEquals(ProblemSeverities.Critical, problem.getSeverity());
    }
}
