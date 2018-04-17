package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session implements Fact {
    private String id;
    private String judgeId;
    private OffsetDateTime start;
    private Duration duration;


    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }
}
