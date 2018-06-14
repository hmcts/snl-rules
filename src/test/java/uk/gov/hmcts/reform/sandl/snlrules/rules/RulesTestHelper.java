package uk.gov.hmcts.reform.sandl.snlrules.rules;

import org.junit.Assert;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;
import uk.gov.hmcts.reform.sandl.snlrules.model.ProblemTypes;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Day;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Month;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Year;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static uk.gov.hmcts.reform.sandl.snlrules.rules.DateTimeHelper.offsetDateTimeOf;

public final class RulesTestHelper {
    private RulesTestHelper() {
    }

    public static List<FactModification> getInsertedProblems(DroolsService droolsService) {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isInserted() && m.getType().equals("Problem"))
            .collect(Collectors.toList());
    }

    public static List<FactModification> getInsertedProblems(DroolsService droolsService, ProblemTypes problemType) {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isInserted() && m.getType().equals("Problem") && m.toString().contains(problemType.name()))
            .collect(Collectors.toList());
    }

    public static List<FactModification> getModifiedProblems(DroolsService droolsService) {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isUpdated() && m.getType().equals("Problem"))
            .collect(Collectors.toList());
    }

    public static List<FactModification> getDeletedProblems(DroolsService droolsService) {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isDeleted() && m.getType().equals("Problem"))
            .collect(Collectors.toList());
    }

    public static void assertProblems(DroolsService droolsService, int inserted, int modified, int deleted) {
        assertThat(getInsertedProblems(droolsService).size())
            .as("Inserted problems count different than expected %s", inserted)
            .isEqualTo(inserted);

        assertThat(getModifiedProblems(droolsService).size())
            .as("Modified problems count different than expected  %s", modified)
            .isEqualTo(modified);

        assertThat(getDeletedProblems(droolsService).size())
            .as("Deleted problems count different than expected %s", deleted)
            .isEqualTo(deleted);
    }

    public static void setDateInRules(KieSession rules, int year, int month, int day) {
        upsertFact(rules, new Year(year));
        upsertFact(rules, new Month(month));
        upsertFact(rules, new Day(day));
    }

    public static void upsertFact(KieSession rules, Fact d) {
        FactHandle factHandle = rules.getFactHandle(d);
        if (factHandle == null) {
            rules.insert(d);
        } else {
            rules.update(factHandle, d);
        }
    }

    public static void assertResults(Map<OffsetDateTime, OffsetDateTime> expectedResults,
                                     OffsetDateTime bookableStart, OffsetDateTime bookableEnd) {
        if (expectedResults.containsKey(bookableStart)) {
            Assert.assertEquals(bookableEnd, expectedResults.get(bookableStart));
        } else {
            fail("invalid results");
        }
    }

    public static void add(Map<OffsetDateTime, OffsetDateTime> expectedResults, String start, String end) {
        expectedResults.put(offsetDateTimeOf(start), offsetDateTimeOf(end));
    }
}
