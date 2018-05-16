package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Judge extends Fact {
    private String name;

    @Override public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int PRIME = 59;
        return PRIME + super.hashCode();
    }
}
