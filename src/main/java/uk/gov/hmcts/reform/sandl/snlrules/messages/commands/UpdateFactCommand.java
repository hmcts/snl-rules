package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.NoArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.List;

@NoArgsConstructor
@Component
public class UpdateFactCommand extends FactCommand {

    @Override
    public List<FactModification> execute(DroolsService droolsService, String data) {
        droolsService.clearFactModifications();

        KieSession session = droolsService.getRulesSession();
        Object fact = deserializeMessage(data, this.getFactType());

        // Only id is used for finding the old fact,
        // all the other values should be the new values the fact would be updated to
        FactHandle factHandle = session.getFactHandle(fact);
        if (factHandle == null) {
            throw new RuntimeException("Fact not found " + fact.toString());
        }

        session.update(factHandle, fact);

        session.fireAllRules();

        return droolsService.getFactModifications();
    }
}
