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
public class HearingType implements Serializable {
    private String code;
    private String description;
}
