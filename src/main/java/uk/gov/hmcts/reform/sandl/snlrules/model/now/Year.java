package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S1700")
public class Year extends Fact {
    private int year;

    public Year(int year) {
        this();
        this.year = year;
    }

    public Year() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "6ddcfae6-b846-4f4a-afab-83f2e47a980a";
    }

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
