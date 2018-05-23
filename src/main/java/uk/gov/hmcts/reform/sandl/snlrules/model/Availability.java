package uk.gov.hmcts.reform.sandl.snlrules.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Availability extends Fact {
    private String id;
    private String judgeId;
    private OffsetDateTime start;
    private Duration duration;

    @JsonIgnore
    public OffsetDateTime getEnd() {
        return start.plus(duration);
    }

    @Override public boolean equals(Object o) {//NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
