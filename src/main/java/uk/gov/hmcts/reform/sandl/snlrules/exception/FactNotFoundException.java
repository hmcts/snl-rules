package uk.gov.hmcts.reform.sandl.snlrules.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such fact")
public class FactNotFoundException extends RuntimeException {
    public FactNotFoundException(String message) {
        super(message);
    }

    public FactNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
