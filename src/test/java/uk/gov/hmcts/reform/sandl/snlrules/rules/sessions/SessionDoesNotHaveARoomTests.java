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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.assertProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.getInsertedProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class SessionDoesNotHaveARoomTests {

    private static final String rulesDefinition = "Sessions";

    public static final String SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START
        = "Session does not have a room 4 weeks or less before start";

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
        setDateInRules(rules, 2018, 02, 01);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_does_not_have_a_room_4_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 04, 05);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService,1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 11);
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START));
        assertProblems(droolsService,0, 0, 1);
    }

    @Test
    public void should_be_problem_when_session_dont_have_a_room_and_date_changes_to_4_weeks_or_nearer_before_start() {
        setDateInRules(rules, 2018, 01, 01);

        rules.insert(new Session(sessionId, judgeId, null,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START));
        assertThat(getInsertedProblems(droolsService)).isEmpty();

        setDateInRules(rules, 2018, 04, 01);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START));

        assertProblems(droolsService,1, 0, 0);

        droolsService.clearFactModifications();
        setDateInRules(rules, 2018, 4, 11);
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_DOES_NOT_HAVE_A_ROOM_4_WEEKS_OR_LESS_BEFORE_START));
        assertProblems(droolsService,0, 0, 1);
    }
}
