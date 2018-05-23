package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S1700")
public class Month extends Fact {
    private int month;

    public Month(int month) {
        this();
        this.month = month;
    }

    public Month() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "ddae85d0-7ec7-4bfe-addf-74ef660604fa";
    }

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
