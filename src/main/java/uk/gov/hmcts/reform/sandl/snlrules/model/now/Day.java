package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class Day extends Fact {
    private int value;

    public Day(int value) {
        this();
        this.value = value;
    }

    public Day() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "527dfdbd-e143-445d-b8d5-fc95a7df7358";
    }

    @Override
    public String toDescription() {
        return "Day: " + value;
    }
}
