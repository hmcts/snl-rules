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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S3437")
public class HearingPart extends Fact {
    private String id;
    private String sessionId;
    private String caseType;
    private Duration duration;
    private OffsetDateTime scheduleStart;
    private OffsetDateTime scheduleEnd;
    private OffsetDateTime createdAt;

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    public HearingPart(String id, String sessionId, String caseType, Duration duration) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseType = caseType;
        this.duration = duration;
    }

    public HearingPart(String id, String sessionId, String caseType, Duration duration, OffsetDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseType = caseType;
        this.duration = duration;
        this.createdAt = createdAt;
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }

    @Override public String toDescription() {
        return "Duration: " + duration + ", Case type: " + caseType + ", Scheduled start: "
            + scheduleStart + ", Scheduled end: " + scheduleEnd + ", Created at: " + createdAt;
    }
}
