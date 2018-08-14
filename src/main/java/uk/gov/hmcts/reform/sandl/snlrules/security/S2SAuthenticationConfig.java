package uk.gov.hmcts.reform.sandl.snlrules.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yaml")
@ConfigurationProperties(prefix = "management.security")
@EnableConfigurationProperties
@NoArgsConstructor
@Getter
@Setter
class S2SAuthenticationConfig {
    JwtCredentials rules;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtCredentials {
        String jwtSecret;
        long jwtExpirationInMs;
    }
}
