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

public class RulesTestHelper {
    public static List<FactModification> getNewProblems(DroolsService droolsService) {
        return droolsService.getFactModifications()
            .stream()
            .filter(m -> m.isInserted() && m.getType().equals("Problem"))
            .collect(Collectors.toList());
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
