package uk.gov.hmcts.reform.sandl.snlrules.rules;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Day;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Month;
import uk.gov.hmcts.reform.sandl.snlrules.model.now.Year;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class RulesTestHelper {
    public static List<FactModification> getInsertedProblems(DroolsService droolsService) {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isInserted() && m.getType().equals("Problem"))
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
}
