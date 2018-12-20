package uk.gov.hmcts.reform.sandl.snlrules.rules.listings;

import lombok.val;
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
public class ListingRequestTargetScheduleAdhering {
    private static final String RULES_DEFINITION = "Listings";
    private static final String LISTING_REQUEST_ID = "702598d4-ff54-460f-8748-e634025af6f0";
    private static final String SESSION_ID = "160dff59-da64-420f-ba0b-c6cdb2c85e52";
    private static final String LISTING_REQUEST_TARGET_SCHEDULE_NOT_ADHERED = "Listing request "
        + "target schedule has not been adhered to when listed";

    private static final String hearingTypeFastTrack = "fast-track-ht";
    private static final String sessionTypeFastTrack = "fast-track-st";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(RULES_DEFINITION);
        droolsService.init();

        rules = droolsService.getRulesSession();
        insertSessionTypesToRules(rules);
    }

    @Test
    public void should_be_no_problem_when_listing_request_target_schedule_is_adhered() {
        setDateInRules(rules,2018, 05, 31);

        rules.insert(new Session(SESSION_ID, null, null,
            OffsetDateTime.of(2018, 6, 2, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), sessionTypeFastTrack));

        OffsetDateTime scheduleStart = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime scheduleEnd = OffsetDateTime.of(2018, 06, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime createdAt = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        rules.insert(new HearingPart(LISTING_REQUEST_ID, SESSION_ID, hearingTypeFastTrack, Duration.ofMinutes(60),
            scheduleStart, scheduleEnd, createdAt));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_TARGET_SCHEDULE_NOT_ADHERED));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_no_problem_when_listing_request_target_schedule_is_not_adhered_but_is_in_the_past() {
        setDateInRules(rules,2018, 06, 16);

        rules.insert(new Session(SESSION_ID, null, null,
            OffsetDateTime.of(2018, 8, 24, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), sessionTypeFastTrack));

        OffsetDateTime scheduleStart = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime scheduleEnd = OffsetDateTime.of(2018, 06, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime createdAt = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        val hearingPart = new HearingPart(LISTING_REQUEST_ID, SESSION_ID, sessionTypeFastTrack, hearingTypeFastTrack,
            Duration.ofMinutes(60));
        hearingPart.setScheduleStart(scheduleStart);
        hearingPart.setScheduleEnd(scheduleEnd);
        hearingPart.setCreatedAt(createdAt);
        rules.insert(hearingPart);

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_TARGET_SCHEDULE_NOT_ADHERED));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_a_problem_when_listing_request_target_schedule_is_not_adhered() {
        setDateInRules(rules,2018, 05, 31);

        rules.insert(new Session(SESSION_ID, null, null,
            OffsetDateTime.of(2018, 8, 24, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), sessionTypeFastTrack));

        OffsetDateTime scheduleStart = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime scheduleEnd = OffsetDateTime.of(2018, 06, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime createdAt = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        val hearingPart = new HearingPart(LISTING_REQUEST_ID, SESSION_ID, sessionTypeFastTrack, hearingTypeFastTrack,
            Duration.ofMinutes(60));
        hearingPart.setScheduleStart(scheduleStart);
        hearingPart.setScheduleEnd(scheduleEnd);
        hearingPart.setCreatedAt(createdAt);
        rules.insert(hearingPart);

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_TARGET_SCHEDULE_NOT_ADHERED));

        assertProblems(droolsService, 1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Listing_policy_violation).size())
            .isEqualTo(1);
    }
}
