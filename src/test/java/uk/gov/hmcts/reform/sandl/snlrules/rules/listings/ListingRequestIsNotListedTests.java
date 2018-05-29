package uk.gov.hmcts.reform.sandl.snlrules.rules.listings;

import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieSession;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.sandl.snlrules.model.HearingPart;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.assertProblems;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class ListingRequestIsNotListedTests {
    private static final String rulesDefinition = "Listings";
    private static final String listingRequestId = "b5821c44-3183-43c7-af97-37bf5c606656";
    private static final String LISTING_REQUEST_OLDER_THAN_60_DAYS_NOT_LISTED = "Listing request is more than "
        + "60 days old, and it has not been listed yet";

    private static final String LISTING_REQUEST_NOT_LISTED_4_WEEKS_OR_NEARER_FROM_TODAY = "Listing request "
        + "target schedule to date is 4 weeks or nearer from today and it has not been listed yet";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }

    @Test
    public void should_be_no_problem_when_listing_request_is_not_older_than_60_days() {
        setDateInRules(rules,2018, 05, 31);

        rules.insert(new HearingPart(listingRequestId, null, "FTRACK", Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 05, 1, 0, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_OLDER_THAN_60_DAYS_NOT_LISTED));

        assertProblems(droolsService, 0, 0, 0);
    }

    @Test
    public void should_be_a_problem_when_listing_request_is_older_than_60_days() {
        setDateInRules(rules,2018, 05, 31);

        rules.insert(new HearingPart(listingRequestId, null, "FTRACK", Duration.ofMinutes(60),
            OffsetDateTime.of(2018, 01, 1, 0, 0, 0, 0, ZoneOffset.UTC)));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_OLDER_THAN_60_DAYS_NOT_LISTED));

        assertProblems(droolsService, 1, 0, 0);
    }

    @Test
    public void should_be_a_problem_when_listing_request_is_4_weeks_or_nearer_and_not_listed() {
        setDateInRules(rules,2018, 05, 31);

        OffsetDateTime scheduleStart = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime scheduleEnd = OffsetDateTime.of(2018, 06, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime createdAt = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        rules.insert(new HearingPart(listingRequestId, null, "FTRACK", Duration.ofMinutes(60),
            scheduleStart, scheduleEnd, createdAt));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_NOT_LISTED_4_WEEKS_OR_NEARER_FROM_TODAY));

        assertProblems(droolsService, 1, 0, 0);
    }

    @Test
    public void should_be_a_problem_when_listing_is_4_weeks_or_nearer_not_listed_then_dissaper() {
        setDateInRules(rules,2018, 05, 31);

        OffsetDateTime scheduleStart = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime scheduleEnd = OffsetDateTime.of(2018, 06, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime createdAt = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        rules.insert(new HearingPart(listingRequestId, null, "FTRACK", Duration.ofMinutes(60),
            scheduleStart, scheduleEnd, createdAt));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_NOT_LISTED_4_WEEKS_OR_NEARER_FROM_TODAY));

        assertProblems(droolsService, 1, 0, 0);

        setDateInRules(rules,2018, 03, 31);

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_NOT_LISTED_4_WEEKS_OR_NEARER_FROM_TODAY));

        assertProblems(droolsService, 0, 0, 1);
    }

    @Test
    public void should_be_no_problem_when_listing_request_is_more_than_4_weeks_and_not_listed() {
        setDateInRules(rules,2018, 05, 31);

        OffsetDateTime scheduleStart = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime scheduleEnd = OffsetDateTime.of(2018, 8, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime createdAt = OffsetDateTime.of(2018, 01, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        rules.insert(new HearingPart(listingRequestId, null, "FTRACK", Duration.ofMinutes(60),
            scheduleStart, scheduleEnd, createdAt));

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter(LISTING_REQUEST_NOT_LISTED_4_WEEKS_OR_NEARER_FROM_TODAY));

        assertProblems(droolsService, 0, 0, 0);
    }
}
