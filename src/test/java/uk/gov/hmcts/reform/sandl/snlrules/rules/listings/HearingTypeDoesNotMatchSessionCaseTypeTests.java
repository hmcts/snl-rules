package uk.gov.hmcts.reform.sandl.snlrules.rules.listings;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class HearingTypeDoesNotMatchSessionCaseTypeTests {

    private final static String rulesDefinition = "Listings";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_hearing_caseType_matches_session_caseType() {
        String sessionId = "f9c351ec-0668-4000-9c39-927518f7389a";
        String roomId = "a3a64d2e-9d42-47d6-ade8-5a5c0d940713";
        String judgeId = "63e52964-5501-479b-b170-08caec4654a0";
        String hearingPartId = "5ff73771-6d1a-4479-9631-3d5af0c944e7";

        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "FTRACK", Duration.ofMinutes(60)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Hearing case type does not match the session case type"));

        assertThat(getNewProblems()).isEmpty();
    }

    @Test
    public void should_be_problem_when_hearing_caseType_doesnot_match_session_caseType() {
        String sessionId = "f9c351ec-0668-4000-9c39-927518f7389a";
        String roomId = "a3a64d2e-9d42-47d6-ade8-5a5c0d940713";
        String judgeId = "63e52964-5501-479b-b170-08caec4654a0";
        String hearingPartId = "5ff73771-6d1a-4479-9631-3d5af0c944e7";

        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 22, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "MTRACK"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "FTRACK", Duration.ofMinutes(60)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Hearing case type does not match the session case type"));
        assertThat(getNewProblems().size()).isEqualTo(1);
    }

    private List<FactModification> getNewProblems() {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isInserted() && m.getType().equals("Problem"))
            .collect(Collectors.toList());
    }
}
