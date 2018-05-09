package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.Data;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.List;

@Data
@Component
public class DeleteFactCommand extends FactCommand {

    @Override
    public List<FactModification> execute(DroolsService droolsService, String data) {
        droolsService.clearFactModifications();

        KieSession session = droolsService.getRulesSession();

        FactHandle factHandle = session.getFactHandle(deserializeMessage(data, this.getFactType()));

        if (factHandle != null) {
            session.delete(factHandle);
            session.fireAllRules();
        }

        return droolsService.getFactModifications();
    }
}
