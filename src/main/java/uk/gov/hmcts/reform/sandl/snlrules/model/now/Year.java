package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class Year extends Fact {
    private int value;

    public Year(int value) {
        this();
        this.value = value;
    }

    public Year() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "6ddcfae6-b846-4f4a-afab-83f2e47a980a";
    }

    @Override
    public String toDescription() {
        return "Year " + value;
    }
}
