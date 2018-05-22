package uk.gov.hmcts.reform.sandl.snlrules.rules.sessions;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Day;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Month;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Year;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SessionsTimeTests {

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
    public void should_be_no_problem_when_session_does_not_have_a_judge_more_than_4_weeks_before_start() {
        // set date to A
        // add sessions without judge taking as date A-5 weeks
        // assert no problem

        setDateInRules(2018, 02, 01);
        // setTimeInRules(9, 0);

        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String roomId = "33362d68-76e3-430a-84c4-23983c448dc2";
        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session does not have a judge 4 weeks or less before start"));

        assertThat(getNewProblems()).isEmpty();
    }

    @Test
    public void should_be_problem_when_session_does_not_have_a_judge_4_weeks_or_nearer_before_start() {
        // set date to A
        // add sessions without judge taking as date A-3 weeks
        // assert problem

        setDateInRules(2018, 04, 05);
        // setTimeInRules(9, 0);

        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String roomId = "33362d68-76e3-430a-84c4-23983c448dc2";
        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session does not have a judge 4 weeks or less before start"));
        //rules.fireAllRules();

        assertThat(getNewProblems().size()).isEqualTo(1);
    }

    @Test
    public void should_be_problem_when_session_does_not_have_a_judge_and_date_changes_to_4_weeks_or_nearer_before_start() {
        // set date to A
        // add sessions without judge taking as date A+8 weeks
        // change date to A+5 weeks
        // should be a problem

        setDateInRules(2018, 01, 01);
        // setTimeInRules(9, 0);

        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String roomId = "33362d68-76e3-430a-84c4-23983c448dc2";
        rules.insert(new Session(sessionId, null, roomId,
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session does not have a judge 4 weeks or less before start"));
        assertThat(getNewProblems()).isEmpty();

        setDateInRules(2018, 04, 01);
        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session does not have a judge 4 weeks or less before start"));

        assertThat(getNewProblems().size()).isEqualTo(1);
    }

    private void setDateInRules(int year, int month, int day) {
        upsertFact(new Year(year));
        upsertFact(new Month(month));
        upsertFact(new Day(day));
    }

    private void upsertFact(Fact d) {
        FactHandle factHandle = rules.getFactHandle(d);
        if (factHandle == null) {
            rules.insert(d);
        } else {
            rules.update(factHandle, d);
        }
    }

//    private void setTimeInRules(int hour, int minute) {
//        rules.insert(new Hour(hour));
//        rules.insert(new Minute(minute));
//    }

    private List<FactModification> getNewProblems() {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isInserted() && m.getType().equals("Problem"))
            .collect(Collectors.toList());
    }
}
