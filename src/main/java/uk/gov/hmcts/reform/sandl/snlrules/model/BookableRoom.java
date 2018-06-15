package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookableRoom extends Fact {
    private String id;
    private String roomId;
    private OffsetDateTime start;
    private Duration duration;

    public BookableRoom(String roomId, OffsetDateTime start, Duration duration) {
        this.roomId = roomId;
        this.start = start;
        this.duration = duration;
        this.id = DigestUtils.md5Hex(this.toString());
    }

    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }

    @Override
    public String toDescription() {
        return ("Start: " + DateTimeUtils.humanizeDate(start) + ", duration: " + duration)
            .replace("null", "N/A");
    }
}
