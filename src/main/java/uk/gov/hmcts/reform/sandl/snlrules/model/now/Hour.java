package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S1700")
public class Hour extends Fact {
    private int hour;

    public Hour(int hour) {
        this();
        this.hour = hour;
    }

    public Hour() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "7c107d5b-30b7-4e48-921c-32294cdb944e";
    }

    @Override public boolean equals(Object o) {//NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
