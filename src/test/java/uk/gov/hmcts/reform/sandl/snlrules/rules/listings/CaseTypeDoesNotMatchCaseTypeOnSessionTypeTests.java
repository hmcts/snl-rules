package uk.gov.hmcts.reform.sandl.snlrules.rules.listings;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.CaseType;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingType;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemTypes;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.model.SessionType;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.assertProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.getInsertedProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class CaseTypeDoesNotMatchCaseTypeOnSessionTypeTests {
    private static final String rulesDefinition = "Listings";

    private static final String CASE_TYPE_DOES_NOT_MATCH_CASE_TYPE_ON_SESSION_TYPE
        = "The case type on the listing request does not match a case type associated to the session type of the session";

    private static final String sessionId = "5af0f655-8af9-497a-873d-e1cb6cb45c3d";
    private static final String roomId = "5e680fb8-da2e-4541-af70-e6ecdb5be00d";
    private static final String judgeId = "ffa78672-bb77-4e2d-9afc-90d9e86cacea";
    private static final String hearingPartId = "3c2fa8d9-c2eb-442d-aded-097f5df27250";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_caseType_matches_sessionType_caseType() {
        setDateInRules(rules,2018, 8, 1);
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "multi-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "multi_2-track-ct", "multi-track-ht", Duration.ofMinutes(60)));

        insertSessionTypesToRules();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(CASE_TYPE_DOES_NOT_MATCH_CASE_TYPE_ON_SESSION_TYPE));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_problem_when_caseType_missing_in_sessionType_caseType() {
        setDateInRules(rules,2018, 8, 1);

        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "fast-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "fast-track-does_not_exist", "fast-track-ht", Duration.ofMinutes(60)));

        insertSessionTypesToRules();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(CASE_TYPE_DOES_NOT_MATCH_CASE_TYPE_ON_SESSION_TYPE));

        assertProblems(droolsService, 1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Listing_request_types_violation).size())
            .isEqualTo(1);
    }

    @Test
    public void should_be_problem_when_hearing_caseType_doesnot_match_sessionType_caseType() {
        setDateInRules(rules,2018, 8, 1);
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "fast-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "multi-track-cs", "other-track-ht", Duration.ofMinutes(60)));

        insertSessionTypesToRules();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(CASE_TYPE_DOES_NOT_MATCH_CASE_TYPE_ON_SESSION_TYPE));

        assertProblems(droolsService,1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Listing_request_types_violation).size())
            .isEqualTo(1);
    }

    private void insertSessionTypesToRules() {
        List<CaseType> fastTrackCt = new ArrayList<>();
        fastTrackCt.add(new CaseType("fast-track-ct", "a description"));
        fastTrackCt.add(new CaseType("fast_2-track-ct", "a description"));
        fastTrackCt.add(new CaseType("fast_3-track-ct", "a description"));

        List<HearingType> fastTrackHt = new ArrayList<>();
        fastTrackHt.add(new HearingType("fast-track-ht", "a description"));
        fastTrackHt.add(new HearingType("fast_2-track-ht", "a description"));
        fastTrackHt.add(new HearingType("fast_3-track-ht", "a description"));

        rules.insert(new SessionType("fast-track-st", "a description", fastTrackCt, fastTrackHt));

        List<CaseType> multiTrackCt = new ArrayList<>();
        multiTrackCt.add(new CaseType("multi-track-ct", "a description"));
        multiTrackCt.add(new CaseType("multi_2-track-ct", "a description"));
        multiTrackCt.add(new CaseType("multi_3-track-ct", "a description"));

        List<HearingType> multiTrackHt = new ArrayList<>();
        multiTrackHt.add(new HearingType("multi-track-ht", "a description"));
        multiTrackHt.add(new HearingType("multi_2-track-ht", "a description"));
        multiTrackHt.add(new HearingType("multi_3-track-ht", "a description"));

        rules.insert(new SessionType("multi-track-st", "a description", multiTrackCt, multiTrackHt));

        List<CaseType> otherTrackCt = new ArrayList<>();
        otherTrackCt.add(new CaseType("other-track-ct", "a description"));
        otherTrackCt.add(new CaseType("other_2-track-ct", "a description"));
        otherTrackCt.add(new CaseType("other_3-track-ct", "a description"));

        List<HearingType> otherTrackHt = new ArrayList<>();
        otherTrackHt.add(new HearingType("other-track-ht", "a description"));
        otherTrackHt.add(new HearingType("other_2-track-ht", "a description"));
        otherTrackHt.add(new HearingType("other_3-track-ht", "a description"));

        rules.insert(new SessionType("other-track-st", "a description", otherTrackCt, otherTrackHt));
    }
}
