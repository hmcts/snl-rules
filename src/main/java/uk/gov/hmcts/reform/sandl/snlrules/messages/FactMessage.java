package uk.gov.hmcts.reform.sandl.snlrules.messages;

import lombok.Data;

@Data
public class FactMessage {
    private String type;
    private String data;
}
