package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.Data;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

@Data
@Component
public class DeleteFactCommand extends FactCommand {
    @Autowired
    private DroolsService droolsService;

    @Override
    public void execute(String data) {
        KieSession session = droolsService.getRulesSession();

        FactHandle factHandle = session.getFactHandle(deserializeMessage(data, this.getFactType()));
        session.delete(factHandle);

        session.fireAllRules();
    }
}
