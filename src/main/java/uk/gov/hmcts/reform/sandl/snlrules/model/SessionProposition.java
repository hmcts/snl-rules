package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionProposition {
    private String judgeId;
    private String roomId;
    private OffsetDateTime start;
    private OffsetDateTime end;
}
