package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("squid:S1700")
public class Minute extends Fact {
    private int minute;

    public Minute(int minute) {
        this();
        this.minute = minute;
    }

    public Minute() {
        this.setId("");
    }

    @Override
    public void setId(String id) {
        this.id = "70edd84a-0722-43fd-9e5f-5b5b40d3322f";
    }


    @Override public boolean equals(Object o) {//NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
