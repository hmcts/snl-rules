package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuppressWarnings("squid:S3437")
public class Session extends Fact implements Serializable {

    private String judgeId;
    private String roomId;
    private OffsetDateTime start;
    private Duration duration;
    private String caseType;

    public Session(OffsetDateTime start, Duration duration) {
        this.start = start;
        this.duration = duration;
    }

    public Session(String id, String judgeId, String roomId, OffsetDateTime start,
                   Duration duration, String caseType) {
        this.id = id;
        this.judgeId = judgeId;
        this.roomId = roomId;
        this.start = start;
        this.duration = duration;
        this.caseType = caseType;
    }

    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }

    public boolean isOverlapping(Session s2) {
        return start.isBefore(s2.getEnd()) && s2.getStart().isBefore(this.getEnd());
    }

    @Override
    public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }

    @Override
    public String toDescription() {
        return ("Start: " + DateTimeUtils.humanizeDate(start) + ", Case type: " + caseType)
            .replace("null", "N/A");
    }
}
