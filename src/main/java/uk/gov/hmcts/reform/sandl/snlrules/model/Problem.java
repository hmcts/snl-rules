package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Problem extends Fact {

    private String message;
    private ProblemTypes type;
    private ProblemSeverities severity;
    private List<ProblemReference> references = new ArrayList<>();

    public Problem(ProblemTypes type, ProblemSeverities severity, String message, ProblemReference... references) {
        this.type = type;
        this.severity = severity;
        this.references.addAll(Arrays.asList(references));
        this.id = DigestUtils.md5Hex(this.toString());
        this.message = message;
    }

    @Override public boolean equals(Object o) { //NOPMD
        return super.equals(o);
    }

    @Override public int hashCode() {
        final int prime = 59;
        return prime + super.hashCode();
    }

    @Override
    public String toDescription() {
        return ("Message: " + message + ", type: " + type + ", severity: " + severity)
            .replace("null", "N/A");
    }
}
