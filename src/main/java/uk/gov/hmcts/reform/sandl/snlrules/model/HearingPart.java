package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S3437")
public class HearingPart extends Fact {
    private String id;
    private String sessionId;
    private String caseType;
    private Duration duration;

    @Override public boolean equals(Object o) {
        return super.equals(o);
    }
}
