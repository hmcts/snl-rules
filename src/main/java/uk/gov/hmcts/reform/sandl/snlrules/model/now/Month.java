package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class Month extends Fact {
    private int value;

    public Month(int value) {
        this();
        this.value = value;
    }

    public Month() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "ddae85d0-7ec7-4bfe-addf-74ef660604fa";
    }

    @Override
    public String toDescription() {
        return "Month: " + value;
    }
}
