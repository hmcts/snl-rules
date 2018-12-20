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
    private String caseTypeCode;
    private String hearingTypeCode;
    private Duration duration;
    private OffsetDateTime scheduleStart;
    private OffsetDateTime scheduleEnd;
    private OffsetDateTime createdAt;

    public HearingPart(String id, String sessionId, String caseTypeCode, String hearingTypeCode, Duration duration) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseTypeCode = caseTypeCode;
        this.hearingTypeCode = hearingTypeCode;
        this.duration = duration;
    }

    public HearingPart(String id, String sessionId, String caseTypeCode, String hearingTypeCode, Duration duration,
                       OffsetDateTime scheduleEnd) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseTypeCode = caseTypeCode;
        this.hearingTypeCode = hearingTypeCode;
        this.duration = duration;
        this.scheduleEnd = scheduleEnd;
    }

    public HearingPart(String id, String sessionId, String caseTypeCode, String hearingTypeCode, Duration duration,
                       OffsetDateTime createdAt, OffsetDateTime scheduleEnd) {
        this.id = id;
        this.sessionId = sessionId;
        this.caseTypeCode = caseTypeCode;
        this.hearingTypeCode = hearingTypeCode;
        this.duration = duration;
        this.createdAt = createdAt;
        this.scheduleEnd = scheduleEnd;
    }

    @Override
    public String toDescription() {
        return ("Duration: " + readableDuration(this.duration) + ", Case type code: " + caseTypeCode
            + ", Hearing type code: " + hearingTypeCode + ", Scheduled start: "
            + humanizeDate(scheduleStart) + ", Scheduled end: " + humanizeDate(scheduleEnd)
            + ", Created at: " + createdAt).replace("null", "N/A");
    }
}
