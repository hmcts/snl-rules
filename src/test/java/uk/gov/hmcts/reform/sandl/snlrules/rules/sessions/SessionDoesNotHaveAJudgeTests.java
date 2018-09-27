package uk.gov.hmcts.reform.sandl.snlrules.rules.sessions;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
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
public class SessionDoesNotHaveAJudgeTests {

    private static final String rulesDefinition = "Sessions";

    private static final String SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START
        = "Session does not have a judge 4 weeks or less before start";
    private static final String SESSION_DOES_NOT_HAVE_A_JUDGE_2_WEEKS_OR_LESS_BEFORE_START
        = "Session does not have a judge 2 weeks or less before start";
    private static final String SESSION_DOES_NOT_HAVE_A_JUDGE_1_DAY_OR_LESS_BEFORE_START
        = "Session does not have a judge 1 day or less before start";

    private static final String SESSION_DOES_NOT_HAVE_A_JUDGE
        = "Session does not have a judge";

    private static final String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
    private static final String roomId = "33362d68-76e3-430a-84c4-23983c448dc2";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_session_does_not_have_a_judge_more_than_4_weeks_before_start() {
        setDateInRules(rules, 2018, 2, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_does_not_have_a_judge_4_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 4, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 29, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 30);
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_judge_and_date_changes_to_4_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 29, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 1);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 30);
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_4_WEEKS_OR_LESS_BEFORE_START));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_judge_and_date_changes_to_2_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 15, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_2_WEEKS_OR_LESS_BEFORE_START));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 1);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_2_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 16);
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_2_WEEKS_OR_LESS_BEFORE_START));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_judge_and_date_changes_to_1_day_or_nearer_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 3, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_1_DAY_OR_LESS_BEFORE_START));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 2);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_1_DAY_OR_LESS_BEFORE_START));
        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 4);
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE_1_DAY_OR_LESS_BEFORE_START));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_judge_and_date_changes_from_15_days_to_14_before_start() {
        setDateInRules(rules, 2018, 1, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 16, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 1);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 2);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertProblems(droolsService, 1, 0, 1);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 17);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_judge_and_date_changes_from_2_days_to_1_before_start() {
        setDateInRules(rules, 2018, 1, 2);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 4, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 2);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertProblems(droolsService, 1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 3);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertProblems(droolsService, 1, 0, 1);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 5);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void problemSeverityShouldChangeWhenTimePassesCheckPoints_whenSessionDoesNotHaveAjudge() {
        setDateInRules(rules, 2018, 4, 1);

        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 30, 9, 0, 0, 0,
                ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 4, 2);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        Problem problem = (Problem) RulesTestHelper.getInsertedProblems(droolsService).get(0).getNewFact();
        Assert.assertEquals(ProblemSeverities.Warning, problem.getSeverity());

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 16);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        problem = (Problem) RulesTestHelper.getInsertedProblems(droolsService).get(0).getNewFact();
        Assert.assertEquals(ProblemSeverities.Urgent, problem.getSeverity());

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 29);
        rules.fireAllRules(new RuleNameStartsWithAgendaFilter(SESSION_DOES_NOT_HAVE_A_JUDGE));
        problem = (Problem) RulesTestHelper.getInsertedProblems(droolsService).get(0).getNewFact();
        Assert.assertEquals(ProblemSeverities.Critical, problem.getSeverity());
    }
}
