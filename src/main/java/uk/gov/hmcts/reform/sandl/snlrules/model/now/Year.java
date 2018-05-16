package uk.gov.hmcts.reform.sandl.snlrules.model.now;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S1700")
public class Year extends Fact {
    private int year;

    @Override public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
