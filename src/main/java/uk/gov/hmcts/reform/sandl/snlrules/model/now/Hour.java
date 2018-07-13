package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class Hour extends Fact {
    private int value;

    public Hour(int value) {
        this();
        this.value = value;
    }

    public Hour() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "7c107d5b-30b7-4e48-921c-32294cdb944e";
    }

    @Override
    public String toDescription() {
        return "Hour: " + value;
    }
}
