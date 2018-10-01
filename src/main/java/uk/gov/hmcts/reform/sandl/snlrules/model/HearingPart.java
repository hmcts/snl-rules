package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;
import java.time.OffsetDateTime;

import static uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils.humanizeDate;
import static uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils.readableDuration;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"squid:S3437", "squid:S2160"}) // S2160 - because Fact controls identity distinction
public class HearingPart extends Fact {

    private String sessionId;
    private String caseType;
    private String hearingType;
    private Duration duration;
    private OffsetDateTime scheduleStart;
    private OffsetDateTime scheduleEnd;
    private OffsetDateTime createdAt;

    public HearingPart(String id, String sessionId, String caseType, String hearingType, Duration duration) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseType = caseType;
        this.hearingType = hearingType;
        this.duration = duration;
    }

    public HearingPart(String id, String sessionId, String caseType, String hearingType, Duration duration,
                       OffsetDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseType = caseType;
        this.hearingType = hearingType;
        this.duration = duration;
        this.createdAt = createdAt;
    }

    @Override
    public String toDescription() {
        return ("Duration: " + readableDuration(this.duration) + ", Case type: " + caseType + ", Hearing type: "
            + hearingType + ", Scheduled start: "
            + humanizeDate(scheduleStart) + ", Scheduled end: " + humanizeDate(scheduleEnd)
            + ", Created at: " + createdAt).replace("null", "N/A");
    }
}
