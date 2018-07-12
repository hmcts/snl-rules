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
@SuppressWarnings("squid:S3437")
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
        return ("Start: " + DateTimeUtils.humanizeDate(start) + ", duration: " + duration)
            .replace("null", "N/A");
    }
}
