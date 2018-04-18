package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Session extends Fact {
    private String id;
    private String judgeId;
    private OffsetDateTime start;
    private Duration duration;


    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }
}
