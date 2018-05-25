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
import static uk.gov.hmcts.reform.sandl.snlrules.rules.RulesTestHelper.setDateInRules;

@RunWith(MockitoJUnitRunner.class)
public class SessionIsOverlistedTests {

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
    public void should_be_no_problem_when_session_is_not_overlisted() {
        setDateInRules(rules, 2018, 04, 1);

        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted and 1 day or less before start"));
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted greater or equal 50 percent and 1 to 3 days before start"));
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted greater or equal 100 percent and 3 to 7 days before start"));
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is listed less or equal 50 percent 7 days before start"));

        assertProblems(droolsService,0, 0, 0);
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

        // 1 day before
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 24, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId_1, sessionId, "FTRACK", Duration.ofMinutes(20)));
        rules.insert(new HearingPart(hearingPartId_2, sessionId, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart(hearingPartId_3, sessionId, "FTRACK", Duration.ofMinutes(25)));
        addSomeDummySessionsAndHearings();


        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted and 1 day or less before start"));

        assertProblems(droolsService,1, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Session_is_overlisted_1_day_or_nearer_before_start).size())
            .isEqualTo(1);
    }

    @Test
    public void should_be_no_problem_when_session_is_not_overlisted_50pct_but_only_30pct_1to3_days_before_start() {
        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";
        String hearingPartId_1 = "96527c3d-a41f-42ab-b907-1607d644b77a";
        String hearingPartId_2 = "5f928573-5601-40ee-97f4-42a03eb9a0b5";
        String hearingPartId_3 = "2fbbf624-c38e-49d3-99f8-df4f29a78a05";

        setDateInRules(rules, 2018, 05, 23);

        // > 1 day before
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 25, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId_1, sessionId, "FTRACK", Duration.ofMinutes(20)));
        rules.insert(new HearingPart(hearingPartId_2, sessionId, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart(hearingPartId_3, sessionId, "FTRACK", Duration.ofMinutes(15)));

        //some other sessions and hearings so we have something else in the rules
        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted greater or equal 50 percent and 1 to 3 days before start"));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_is_overlisted_50pct_1to3_days_before_start() {
        String sessionId_1 = "96462d68-76e3-430a-84c4-23983c448dc2";
        String sessionId_2 = "010492c1-ce9c-4ecd-b6fe-fdffd323c18f";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";

        setDateInRules(rules, 2018, 05, 23);

        // 1 day before
        rules.insert(new Session(sessionId_1, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 25, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("b8146183-1032-4015-8104-c4f38802b19a", sessionId_1, "FTRACK", Duration.ofMinutes(100)));

        // 2 days before
        rules.insert(new Session(sessionId_2, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 26, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("96527c3d-a41f-42ab-b907-1607d644b77a", sessionId_2, "FTRACK", Duration.ofMinutes(20)));
        rules.insert(new HearingPart("5f928573-5601-40ee-97f4-42a03eb9a0b5", sessionId_2, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart("2fbbf624-c38e-49d3-99f8-df4f29a78a05", sessionId_2, "FTRACK", Duration.ofMinutes(45)));

        //some other sessions and hearings so we have something else in the rules
        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted greater or equal 50 percent and 1 to 3 days before start"));

        assertProblems(droolsService,2, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Session_is_overlisted_greater_or_equal_50_percent_and_1_to_3_days_before_start).size())
            .isEqualTo(2);
    }

    @Test
    public void should_be_no_problem_when_session_is_not_overlisted_100pct_but_only_60pct_3to7_days_before_start() {
        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";
        String hearingPartId_1 = "96527c3d-a41f-42ab-b907-1607d644b77a";
        String hearingPartId_2 = "5f928573-5601-40ee-97f4-42a03eb9a0b5";
        String hearingPartId_3 = "2fbbf624-c38e-49d3-99f8-df4f29a78a05";

        setDateInRules(rules, 2018, 05, 23);

        // > 1 day before
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 28, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId_1, sessionId, "FTRACK", Duration.ofMinutes(20)));
        rules.insert(new HearingPart(hearingPartId_2, sessionId, "FTRACK", Duration.ofMinutes(30)));
        rules.insert(new HearingPart(hearingPartId_3, sessionId, "FTRACK", Duration.ofMinutes(50)));

        //some other sessions and hearings so we have something else in the rules
        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted greater or equal 100 percent and 3 to 7 days before start"));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_is_overlisted_100pct_3to7_days_before_start() {
        String sessionId_1 = "96462d68-76e3-430a-84c4-23983c448dc2";
        String sessionId_2 = "010492c1-ce9c-4ecd-b6fe-fdffd323c18f";
        String sessionId_3 = "749960cb-bd25-4513-9907-96b06593afac";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";

        setDateInRules(rules, 2018, 05, 23);

        // 4 day before
        rules.insert(new Session(sessionId_1, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 27, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("b8146183-1032-4015-8104-c4f38802b19a", sessionId_1, "FTRACK", Duration.ofMinutes(200)));

        // 5 days before
        rules.insert(new Session(sessionId_2, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 28, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("96527c3d-a41f-42ab-b907-1607d644b77a", sessionId_2, "FTRACK", Duration.ofMinutes(40)));
        rules.insert(new HearingPart("5f928573-5601-40ee-97f4-42a03eb9a0b5", sessionId_2, "FTRACK", Duration.ofMinutes(50)));
        rules.insert(new HearingPart("2fbbf624-c38e-49d3-99f8-df4f29a78a05", sessionId_2, "FTRACK", Duration.ofMinutes(45)));

        // 6 days before
        rules.insert(new Session(sessionId_3, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 29, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("15e461e7-a846-41e5-bb99-fd6c780e82d3", sessionId_3, "FTRACK", Duration.ofMinutes(70)));
        rules.insert(new HearingPart("03756aa6-2be7-4dd6-a636-dafe694cc23c", sessionId_3, "FTRACK", Duration.ofMinutes(60)));

        //some other sessions and hearings so we have something else in the rules
        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is overlisted greater or equal 100 percent and 3 to 7 days before start"));

        assertProblems(droolsService,3, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Session_is_overlisted_greater_or_equal_100_percent_and_3_to_7_days_before_start).size())
            .isEqualTo(3);
    }

    @Test
    public void should_be_no_problem_when_session_is_listed_70pct_less_than_7_days_before_start() {
        String sessionId = "96462d68-76e3-430a-84c4-23983c448dc2";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";
        String hearingPartId_1 = "96527c3d-a41f-42ab-b907-1607d644b77a";
        String hearingPartId_2 = "5f928573-5601-40ee-97f4-42a03eb9a0b5";
        String hearingPartId_3 = "2fbbf624-c38e-49d3-99f8-df4f29a78a05";

        setDateInRules(rules, 2018, 05, 23);

        // 4 day before
        rules.insert(new Session(sessionId, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 28, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart(hearingPartId_1, sessionId, "FTRACK", Duration.ofMinutes(20)));
        rules.insert(new HearingPart(hearingPartId_2, sessionId, "FTRACK", Duration.ofMinutes(15)));
        rules.insert(new HearingPart(hearingPartId_3, sessionId, "FTRACK", Duration.ofMinutes(5)));

        //some other sessions and hearings so we have something else in the rules
        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is listed less or equal 50 percent 7 days before start"));

        assertProblems(droolsService,0, 0, 0);
    }

    @Test
    public void should_be_problem_when_session_is_listed_less_or_equals_50pct_less_than_7_days_before_start() {
        String sessionId_1 = "96462d68-76e3-430a-84c4-23983c448dc2";
        String sessionId_2 = "010492c1-ce9c-4ecd-b6fe-fdffd323c18f";
        String sessionId_3 = "749960cb-bd25-4513-9907-96b06593afac";
        String sessionId_4 = "32261ef1-d556-456f-9d62-e81a4ecd44a9";
        String judgeId = "78d4d025-7ebf-4ccd-a9ed-fe12c9bf26ab";
        String roomId = "bbe08aac-bf0f-415f-bffe-16242ee6964d";

        setDateInRules(rules, 2018, 05, 23);

        // 4 day before
        rules.insert(new Session(sessionId_1, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 27, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("b8146183-1032-4015-8104-c4f38802b19a", sessionId_1, "FTRACK", Duration.ofMinutes(20)));

        // 5 days before
        rules.insert(new Session(sessionId_2, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 28, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("96527c3d-a41f-42ab-b907-1607d644b77a", sessionId_2, "FTRACK", Duration.ofMinutes(10)));
        rules.insert(new HearingPart("5f928573-5601-40ee-97f4-42a03eb9a0b5", sessionId_2, "FTRACK", Duration.ofMinutes(7)));
        rules.insert(new HearingPart("2fbbf624-c38e-49d3-99f8-df4f29a78a05", sessionId_2, "FTRACK", Duration.ofMinutes(5)));

        // 6 days before
        rules.insert(new Session(sessionId_3, judgeId, roomId,
            OffsetDateTime.of(2018, 5, 29, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "FTRACK"));

        rules.insert(new HearingPart("15e461e7-a846-41e5-bb99-fd6c780e82d3", sessionId_3, "FTRACK", Duration.ofMinutes(10)));
        rules.insert(new HearingPart("03756aa6-2be7-4dd6-a636-dafe694cc23c", sessionId_3, "FTRACK", Duration.ofMinutes(19)));

        //some other sessions and hearings so we have something else in the rules
        addSomeDummySessionsAndHearings();

        droolsService.clearFactModifications();
        rules.fireAllRules(new RuleNameEqualsAgendaFilter("Session is listed less or equal 50 percent 7 days before start"));

        assertProblems(droolsService,3, 0, 0);
        assertThat(getInsertedProblems(droolsService,
            ProblemTypes.Session_is_listed_less_or_equal_50_percent_7_days_before_start).size())
            .isEqualTo(3);
    }

    private void addSomeDummySessionsAndHearings() {
        //some other sessions and hearings that do not make any conflicts so we have something else in the rules
        rules.insert(new Session("422b74f5-8645-4355-8570-3142348ff299", "144f815d-d072-4069-8493-9e8cc5e4153c", "41242d5c-9d8e-4147-8c40-594a17308fda",
            OffsetDateTime.of(2018, 6, 15, 11, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(60), "MTRACK"));
        rules.insert(new HearingPart("1abb32b7-9d90-484a-beeb-857d1ce9143a", "422b74f5-8645-4355-8570-3142348ff299", "MTRACK", Duration.ofMinutes(20)));
    }
}
