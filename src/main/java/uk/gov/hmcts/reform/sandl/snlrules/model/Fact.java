package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public abstract class Fact implements Serializable {
    protected String id;

    protected boolean canEqual(Object other) {
        return other instanceof Fact;
    }

    @Override public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Fact)) {
            return false;
        }
        Fact other = (Fact) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        return !(this.getId() == null ? other.getId() != null : !this.getId().equals(other.getId()));
    }

    @Override public int hashCode() {
        final int prime = 59;
        int result = 1;
        result = result * prime + (this.getId() == null ? 43 : this.getId().hashCode());
        return result;
    }

    public abstract String toDescription();
}
