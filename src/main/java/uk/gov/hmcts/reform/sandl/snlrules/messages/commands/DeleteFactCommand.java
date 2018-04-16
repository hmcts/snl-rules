package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import lombok.Data;
import org.apache.commons.lang.NotImplementedException;
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
        throw new NotImplementedException("mot implmented yet");
    }
}
