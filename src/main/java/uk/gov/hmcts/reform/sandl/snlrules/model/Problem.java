package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Problem extends Fact {
    private String id;
    private String message;
    private ProblemTypes type;
    private String references;

    public Problem(ProblemTypes type, String references) {
        this.type = type;
        this.references = references;

        this.id = DigestUtils.md5Hex(this.toString());
        this.message = String.format("%s for %s", type, references);
    }

    // depreciated
    public Problem(String id, String message) {
        this.id = id;
        this.message = message;
    }

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }
}
