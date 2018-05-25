package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
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

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
