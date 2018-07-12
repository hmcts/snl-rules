package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S3437")
public class HearingPart extends Fact {

    private String sessionId;
    private String caseType;
    private Duration duration;
    private OffsetDateTime scheduleStart;
    private OffsetDateTime scheduleEnd;
    private OffsetDateTime createdAt;

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

    public HearingPart(String id, String sessionId, String caseType, Duration duration, OffsetDateTime scheduleStart,
                       OffsetDateTime scheduleEnd, OffsetDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseType = caseType;
        this.duration = duration;
        this.scheduleStart = scheduleStart;
        this.scheduleEnd = scheduleEnd;
        this.createdAt = createdAt;
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
        return ("Duration: " + duration + ", Case type: " + caseType + ", Scheduled start: "
            + DateTimeUtils.humanizeDate(scheduleStart) + ", Scheduled end: " + DateTimeUtils.humanizeDate(scheduleEnd)
            + ", Created at: " + createdAt).replace("null", "N/A");
    }
}
