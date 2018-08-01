package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class Minute extends Fact {
    private int value;

    public Minute(int value) {
        this();
        this.value = value;
    }

    public Minute() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "70edd84a-0722-43fd-9e5f-5b5b40d3322f";
    }

    @Override
    public String toDescription() {
        return "Minute: " + value;
    }
}
