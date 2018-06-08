package uk.gov.hmcts.reform.sandl.snlrules.rules.queries;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Room;
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
            Duration.ofHours(1)));

        rules.insert(new Availability("2", "room1",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("3", "judge1",
            OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(1)));

        rules.insert(new Availability("4", "room1",
            OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(2)));


        rules.insert(new Availability("5", "judge2",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(1)));

        rules.insert(new Availability("6", "room2",
            OffsetDateTime.of(2018, 4, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(3)));

        rules.insert(new Availability("7", "judge2",
            OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(1)));

        rules.insert(new Availability("8", "room2",
            OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC),
            Duration.ofHours(2)));


        OffsetDateTime from = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime to = OffsetDateTime.of(2018, 5, 10, 9, 0, 0, 0, ZoneOffset.UTC);

        QueryResults results = rules.getQueryResults("search", from, to);

        for ( QueryResultsRow row : results ) {
            Room room = ( Room ) row.get( "$room" );
            System.out.print( room.toDescription() + " " );
            Availability roomAval = ( Availability ) row.get( "$avalRoom" );
            System.out.print( roomAval.toDescription() );

            Judge judge = ( Judge ) row.get( "$judge" );
            System.out.print( judge.toDescription() + " " );
            Availability judgeAval = ( Availability ) row.get( "$avalJudge" );
            System.out.print( judgeAval.toDescription() + "\n" );
        }

    }
}
