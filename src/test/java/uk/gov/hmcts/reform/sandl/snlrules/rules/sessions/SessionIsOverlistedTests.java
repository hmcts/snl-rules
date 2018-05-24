package uk.gov.hmcts.reform.sandl.snlrules.rules.sessions;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemTypes;
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
public class SessionIsOverlistedTests {

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
    public void should_be_no_problem_when_session_is_overlisted_more_than_month_or_less_before_start() {
        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";
        String hearingPartId_1 = "96527c3d-a41f-42ab-b907-1607d644b77a";
        String hearingPartId_2 = "5f928573-5601-40ee-97f4-42a03eb9a0b5";
        String hearingPartId_3 = "2fbbf624-c38e-49d3-99f8-df4f29a78a05";

        setDateInRules(rules, 2018, 04, 1);

        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 24, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId_1, sessionId, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart(hearingPartId_2, sessionId, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart(hearingPartId_3, sessionId, "FTRACK", Duration.ofMinutes(30)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted and 1 day or less before start"));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_is_overlisted_1_day_or_less_before_start() {
        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";
        String hearingPartId_1 = "96527c3d-a41f-42ab-b907-1607d644b77a";
        String hearingPartId_2 = "5f928573-5601-40ee-97f4-42a03eb9a0b5";
        String hearingPartId_3 = "2fbbf624-c38e-49d3-99f8-df4f29a78a05";

        setDateInRules(rules, 2018, 05, 23);

        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 24, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId_1, sessionId, "FTRACK", Duration.ofMinutes(20)));
        rules.insert(new HearingPart(hearingPartId_2, sessionId, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart(hearingPartId_3, sessionId, "FTRACK", Duration.ofMinutes(25)));


        //some other sessions and hearings so we have something else in the rules
        rules.insert(new Session("422b74f5-8645-4355-8570-3142348ff299", "144f815d-d072-4069-8493-9e8cc5e4153c", "41242d5c-9d8e-4147-8c40-594a17308fda",
            OffsetDateTime.of(2018, 5, 24, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "MTRACK"));
        rules.insert(new HearingPart("1abb32b7-9d90-484a-beeb-857d1ce9143a", "422b74f5-8645-4355-8570-3142348ff299", "MTRACK", Duration.ofMinutes(20)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted and 1 day or less before start"));

        assertProblems(droolsService,1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Session_is_overlisted_1_day_or_nearer_before_start).size())
            .isEqualTo(1);
    }
}
