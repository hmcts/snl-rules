package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.NoArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.exception.FactCommandException;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.List;

@NoArgsConstructor
@Component
public class DeleteFactCommand extends FactCommand {

    @Override
    public List<FactModification> execute(DroolsService droolsService, String data) {
        droolsService.clearFactModifications();

        KieSession session = droolsService.getRulesSession();
        Object fact = deserializeMessage(data, this.getFactType());

        FactHandle factHandle = session.getFactHandle(fact);

        if (factHandle == null) {
            throw new FactCommandException("Fact not found " + fact.toString());
        }

        session.delete(factHandle);
        session.fireAllRules();

        return droolsService.getFactModifications();
    }
}
