package uk.gov.hmcts.reform.sandl.snlrules.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("squid:S2160") // S2160 - because Fact controls identity distinction
public class SessionType extends Fact {
    private List<CaseType> caseTypes;
    private List<HearingType> hearingTypes;

    public SessionType(String code, String description, List<CaseType> caseTypes, List<HearingType> hearingTypes) {
        this.setCode(code);
        this.caseTypes = caseTypes;
        this.hearingTypes = hearingTypes;
    }

    public void setCode(String code) {
        this.id = code;
    }

    public String getCode() {
        return this.id;
    }

    @Override public String toDescription() {
        return ("Code: " + getCode()).replace("null", "N/A");
    }

    public boolean containsCaseTypeByCode(String code) {
        return caseTypes.stream().anyMatch(ct -> ct.getCode().equalsIgnoreCase(code));
    }

    public boolean containsHearingTypeByCode(String code) {
        return hearingTypes.stream().anyMatch(ht -> ht.getCode().equalsIgnoreCase(code));
    }

    public boolean hasCaseTypes() {
        return !caseTypes.isEmpty();
    }

    public boolean hasHearingTypes() {
        return !hearingTypes.isEmpty();
    }

}
