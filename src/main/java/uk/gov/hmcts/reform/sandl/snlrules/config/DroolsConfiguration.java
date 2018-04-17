package uk.gov.hmcts.reform.sandl.snlrules.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@EnableConfigurationProperties
@ConfigurationProperties("drools")
@Validated
public class DroolsConfiguration {

    @Valid
    @NotNull
    private String rulesKSession;

    public String getRulesKSession() {
        return rulesKSession;
    }

    public void setRulesKSession(String rulesKSession) {
        this.rulesKSession = rulesKSession;
    }
}
