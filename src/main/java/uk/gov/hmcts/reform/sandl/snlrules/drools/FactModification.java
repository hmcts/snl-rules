package uk.gov.hmcts.reform.sandl.snlrules.drools;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import uk.gov.hmcts.reform.sandl.snlrules.model.Fact;

@Data
public class FactModification {
    private Fact oldFact;
    private Fact newFact;

    public FactModification(Fact oldFact, Fact newFact) {
        this.oldFact = oldFact;
        this.newFact = newFact;
    }

    @JsonIgnore
    public Class getFactClass() {
        return oldFact != null ? oldFact.getClass() : newFact != null ? newFact.getClass() : null;
    }

    public String getType() {
        return getFactClass() != null ? getFactClass().getSimpleName() : null;
    }

    @JsonIgnore
    public boolean isInserted() {
        return oldFact == null && newFact != null;
    }

    @JsonIgnore
    public boolean isDeleted() {
        return oldFact != null && newFact == null;
    }

    @JsonIgnore
    public boolean isUpdated() {
        return oldFact != null && newFact != null;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return oldFact == null && newFact == null;
    }
}
