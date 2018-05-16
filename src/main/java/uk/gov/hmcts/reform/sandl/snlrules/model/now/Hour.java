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
public class Hour extends Fact {
    private int hour;

    @Override public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int PRIME = 59;
        return PRIME + super.hashCode();
    }
}
