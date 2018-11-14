package uk.gov.hmcts.reform.sandl.snlrules.model.reload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;
import uk.gov.hmcts.reform.sandl.snlrules.utils.DateTimeUtils;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class ReloadStatus extends Fact {
    private OffsetDateTime startedAt;
    private OffsetDateTime finishedAt;

    public ReloadStatus(OffsetDateTime startedAt, OffsetDateTime finishedAt) {
        this();
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public ReloadStatus() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "a1df936f-dde7-4305-a100-4ff1ba1eb5c2";
    }

    @Override
    public String toDescription() {
        return "Started at: " + DateTimeUtils.humanizeDate(startedAt)
            + ", Finished at: " + DateTimeUtils.humanizeDate(finishedAt);
    }
}
