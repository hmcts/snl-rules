package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.util.List;

@Data
@Component
public class InsertFactCommand extends FactCommand {

    @Autowired
    private DroolsService droolsService;

    @Override
    public List<FactModification> execute(String data) {
        droolsService.clearFactModifications();

        droolsService.getRulesSession().insert(deserializeMessage(data, this.getFactType()));
        droolsService.getRulesSession().fireAllRules();

        return droolsService.getFactModifications();
    }
}
