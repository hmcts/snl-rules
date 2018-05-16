package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S3437")
public class Session extends Fact implements Serializable {
    private String id;
    private String judgeId;
    private String roomId;
    private OffsetDateTime start;
    private Duration duration;
    private String caseType;

    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }

    @Override public boolean equals(Object o) {
        return super.equals(o);
    }
}
