package uk.gov.hmcts.reform.sandl.snlrules.messages.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

@Data
public abstract class FactCommand {
    private Class factType;

    public abstract void execute(String data);

    private ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(FAIL_ON_EMPTY_BEANS, false)
            .configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    protected <T> T deserializeMessage(String message, Class<T> messageClass) {
        try {
            return objectMapper.readValue(message, messageClass);
        } catch (IOException e) {
            //TODO add logger
            throw new WebApplicationException("Incorrect JSON data, cannot deserialize.", e);
        }
    }
}
