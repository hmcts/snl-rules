package uk.gov.hmcts.reform.sandl.snlrules.rules.listings;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
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
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.insertSessionTypesToRules;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class SessionTypeIsNotSuitableForListingRequestTests {
    private static final String rulesDefinition = "Listings";

    private static final String SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST
        = "The session type is not suitable for this listing request";

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
        setDateInRules(rules,2018, 8, 1);
        insertSessionTypesToRules(rules);
    }


    @Test
    public void should_be_no_problem_when_sessionType_has_no_caseType_and_no_hearingType() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "no_association-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "other_2-track-ct", "other-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_sessionType_matches_caseType_and_hearingType() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "fast-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "fast_2-track-ct", "fast_3-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_sessionType_matches_caseType_but_not_hearingType() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "fast-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "fast_2-track-ct", "other-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_sessionType_matches_hearingType_but_not_caseType() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "other-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "fast-track-ct", "other_3-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_problem_when_sessionType_does_not_match_both_caseType_and_hearingType() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "fast-track-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "multi-track-cs", "other-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService,1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Listing_request_types_violation).size())
            .isEqualTo(1);
    }

    @Test
    public void should_be_problem_when_sessionType_does_not_match_caseType_and_no_hearingType_association() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "onlyCT-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "multi-track-cs", "multi-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService,1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Listing_request_types_violation).size())
            .isEqualTo(1);
    }

    @Test
    public void should_be_problem_when_sessionType_does_not_match_hearingType_and_no_caseType_association() {
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 9, 4, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "onlyHT-st"));

        rules.insert(new HearingPart(hearingPartId, sessionId, "other_2-track-cs", "other_2-track-ht",
            Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 10, 4, 9, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService,1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Listing_request_types_violation).size())
            .isEqualTo(1);
    }

    @Test
    public void should_be_no_problem_when_sessionType_does_not_match_both_cType_and_hType_but_is_in_the_past() {
        should_be_problem_when_sessionType_does_not_match_both_caseType_and_hearingType();
        setDateInRules(rules,2018, 9, 5);

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(SESSION_TYPE_IS_NOT_SUITABLE_FOR_THIS_LISTING_REQUEST));

        assertProblems(droolsService,0, 0, 1);
    }
}
