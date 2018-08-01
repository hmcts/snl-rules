package uk.gov.hmcts.reform.sandl.snlrules.exception;

public class FactCommandException extends RuntimeException {
    public FactCommandException(String message) {
        super(message);
    }

    public FactCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
