package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.hmcts.reform.sandl.snlrules.drools.FactModification;
import uk.gov.hmcts.reform.sandl.snlrules.exception.FactCommandException;
import uk.gov.hmcts.reform.sandl.snlrules.services.DroolsService;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

@Setter
@Getter
@NoArgsConstructor
public abstract class FactCommand {
    private Class factType;

    private ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(FAIL_ON_EMPTY_BEANS, false)
            .setDateFormat(new ISO8601DateFormat())
            .configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    public abstract List<FactModification> execute(DroolsService droolsService, String data);

    protected <T> T deserializeMessage(String message, Class<T> messageClass) {
        try {
            return objectMapper.readValue(message, messageClass);
        } catch (IOException e) {
            throw new FactCommandException("Incorrect JSON data, cannot deserialize.", e);
        }
    }
}
