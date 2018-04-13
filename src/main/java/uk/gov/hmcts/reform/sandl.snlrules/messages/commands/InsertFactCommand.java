package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

@Data
@Component
public class InsertFactCommand extends FactCommand {

    @Autowired
    private DroolsService droolsService;

    @Override
    public void execute(String data) {
        droolsService.getRulesSession().insert(deserializeMessage(data, this.getFactType()));
    }
}
