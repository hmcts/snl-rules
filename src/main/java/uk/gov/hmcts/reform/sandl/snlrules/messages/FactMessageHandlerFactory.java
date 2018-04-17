package uk.gov.hmcts.reform.sandl.snlrules.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.sandl.snlrules.messages.commands.DeleteFactCommand;
import uk.gov.hmcts.reform.sandl.snlrules.messages.commands.FactCommand;
import uk.gov.hmcts.reform.sandl.snlrules.messages.commands.InsertFactCommand;
import uk.gov.hmcts.reform.sandl.snlrules.messages.commands.UpdateFactCommand;
import uk.gov.hmcts.reform.sandl.snlrules.model.Availability;
import uk.gov.hmcts.reform.sandl.snlrules.model.Judge;
import uk.gov.hmcts.reform.sandl.snlrules.model.Session;

import java.util.HashMap;
import java.util.Map;

@Component
public class FactMessageHandlerFactory {

    private final Map<String, Class> availableCommands;
    private final Map<String, Class> availableFacts;

    @Autowired
    private ApplicationContext context;

    public FactMessageHandlerFactory() {
        this.availableCommands = new HashMap<>();
        this.availableCommands.put("insert", InsertFactCommand.class);
        this.availableCommands.put("delete", DeleteFactCommand.class);
        this.availableCommands.put("update", UpdateFactCommand.class);


        this.availableFacts = new HashMap<>();
        this.availableFacts.put("judge", Judge.class);
        this.availableFacts.put("availability", Availability.class);
        this.availableFacts.put("session", Session.class);
    }

    public FactCommand create(String type) {
        String[] typeSplit = type.split("-");

        if (typeSplit.length != 2) {
            throw new RuntimeException("Type of the message is not supported: " + type);
        }

        String cmd = typeSplit[0];

        if (!availableCommands.containsKey(cmd)) {
            throw new RuntimeException("Unsupported command in message: " + cmd);
        }

        String dataType = typeSplit[1];

        if (!availableFacts.containsKey(dataType)) {
            throw new RuntimeException("Unsupported data type in message: " + dataType);
        }

        FactCommand factCommand = (FactCommand) context.getBean(availableCommands.get(cmd));
        factCommand.setFactType(availableFacts.get(dataType));

        return factCommand;
    }
}
