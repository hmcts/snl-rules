package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
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

    public boolean isOverlapping(Session s2) {
        return start.isBefore(s2.getEnd()) && s2.getStart().isBefore(this.getEnd());
    }

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }

    @Override public String toDescription() {
        return "Start: " + start + ", Case type: " + caseType;
    }
}
