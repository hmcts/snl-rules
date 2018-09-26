package uk.gov.hmcts.reform.sandl.snlrules.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@SuppressWarnings({"squid:S3437", "squid:S2160"}) // S2160 - because Fact controls identity distinction
public class Availability extends Fact {

    private String judgeId;
    private String roomId;
    private OffsetDateTime start;
    private Duration duration;

    public Availability(String id, String judgeId, String roomId, OffsetDateTime start, Duration duration) {
        this.id = id;
        this.judgeId = judgeId;
        this.roomId = roomId;
        this.start = start;
        this.duration = duration;
    }

    @JsonIgnore
    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }

    @Override
    public String toDescription() {
        String readableDuration = DateTimeUtils.readableDuration(this.duration);
        return ("Start: " + DateTimeUtils.humanizeDate(start) + ", duration: " + readableDuration)
            .replace("null", "N/A");
    }
}
