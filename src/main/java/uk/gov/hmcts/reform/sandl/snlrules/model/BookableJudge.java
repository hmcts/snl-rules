package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.io.Serializable;
import java.time.Duration;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuppressWarnings({"squid:S3437", "squid:S2160"}) // S2160 - because Fact controls identity distinction
public class BookableJudge extends Fact implements Serializable {

    private String judgeId;
    private OffsetDateTime start;
    private Duration duration;

    public BookableJudge(String judgeId, OffsetDateTime start, Duration duration) {
        this.judgeId = judgeId;
        this.start = start;
        this.duration = duration;
        this.id = DigestUtils.md5Hex(this.toString());
    }

    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }

    @Override
    public String toDescription() {
        return ("Start: " + DateTimeUtils.humanizeDate(start) + ", duration: " + duration)
            .replace("null", "N/A");
    }
}
