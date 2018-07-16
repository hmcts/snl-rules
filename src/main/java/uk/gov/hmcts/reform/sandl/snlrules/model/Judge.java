package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class Judge extends Fact {
    private String name;

    public Judge(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override public String toDescription() {
        return ("Name: " + name).replace("null", "N/A");
    }
}
