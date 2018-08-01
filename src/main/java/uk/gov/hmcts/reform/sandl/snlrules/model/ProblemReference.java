package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProblemReference implements Serializable {
    private String factId;
    private String fact;
    private String description;

    public ProblemReference(Fact fact) {
        this.factId = fact.getId();
        this.fact = fact.getClass().getSimpleName();
        this.description = fact.toDescription();
    }
}
