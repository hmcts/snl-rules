package uk.gov.hmcts.reform.sandl.snlrules.rules.queries;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class QueriesTests {
    private static final String rulesDefinition = "Sessions";

    private DroolsService droolsService;
    private KieSession rules;

    @Before
    public void setup() {
        droolsService = new DroolsService(rulesDefinition);
        droolsService.init();

        rules = droolsService.getRulesSession();
    }



    // https://docs.jboss.org/drools/release/7.7.0.Final/drools-docs/html_single/index.html#_querysection
    @Test
    public void testQuery() {

        rules.insert(new Room("room1", "Room A"));
        rules.insert(new Judge("judge1", "John Harris"));

        rules.insert(new Room("room2", "Room B"));
        rules.insert(new Judge("judge2", "John Doe"));

        rules.insert(new Availability("1", "judge1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("2", "room1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Session("15", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 10, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(15), "FTRACK"));
        rules.insert(new Session("16", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 11, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(30), "FTRACK"));

        rules.insert(new Session("17", "judge1", null,
            OffsetDateTime.of(2018, 4, 10, 11, 40, 0, 0, ZoneOffset.UTC),
            Duration.ofMinutes(10), "FTRACK"));

        rules.fireAllRules();



        OffsetDateTime from = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);

        QueryResults results = rules.getQueryResults("dupa", from, to);

        for ( QueryResultsRow row : results ) {
            Exception room = ( Exception ) row.get( "$ex" );
            System.out.println( room.getMessage() + " " );

        }

    }
}
