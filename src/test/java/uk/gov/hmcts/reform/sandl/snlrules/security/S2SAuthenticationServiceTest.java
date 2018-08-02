package uk.gov.hmcts.reform.sandl.snlrules.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class S2SAuthenticationServiceTest {

    static final String SERVICE_NAME = "snl-events";
    static final String SECRET_RULES = "FakeTestSecret";
    static final int DEFAULT_EXPIRY = 5000;
    private S2SAuthenticationConfig config;
    private S2SAuthenticationService s2SAuthenticationService;

    @Before
    public void setup() {
        S2SAuthenticationConfig s2sConfig = Mockito.mock(S2SAuthenticationConfig.class);
        when(s2sConfig.getRules())
            .thenReturn(new S2SAuthenticationConfig.JwtCredentials(SECRET_RULES, 1001));
        config = s2sConfig;

        s2SAuthenticationService = new S2SAuthenticationService(config);
    }

    @Test
    public void validateToken_returnsTrue_forValidSecretAndTimeout() {
        when(config.getRules())
            .thenReturn(new S2SAuthenticationConfig.JwtCredentials(SECRET_RULES, DEFAULT_EXPIRY));

        final String token = createToken(SECRET_RULES, DEFAULT_EXPIRY, SERVICE_NAME);

        boolean result = this.s2SAuthenticationService.validateToken(token);

        assertThat(result).isTrue();
    }

    @Test
    public void validateToken_returnsFalse_forInvalidServiceName() {
        when(config.getRules())
            .thenReturn(new S2SAuthenticationConfig.JwtCredentials(SECRET_RULES, DEFAULT_EXPIRY));

        final String token = createToken(SECRET_RULES, DEFAULT_EXPIRY, "snl-INVALID");
        boolean result = this.s2SAuthenticationService.validateToken(token);

        assertThat(result).isFalse();
    }

    @Test
    public void validateToken_returnsFalse_forValidSecretAndWrongTimeout() {
        final String token = createToken(SECRET_RULES, DEFAULT_EXPIRY, SERVICE_NAME);

        when(config.getRules())
            .thenReturn(new S2SAuthenticationConfig.JwtCredentials(SECRET_RULES, DEFAULT_EXPIRY - 1));
        boolean result = this.s2SAuthenticationService.validateToken(token);

        assertThat(result).isFalse();
    }

    @Test
    public void validateToken_returnsFalse_forInValidSecretAndWrongTimeout() {
        final String token = createToken(SECRET_RULES, DEFAULT_EXPIRY, SERVICE_NAME);

        when(config.getRules())
            .thenReturn(new S2SAuthenticationConfig
                .JwtCredentials(SECRET_RULES + "A", DEFAULT_EXPIRY - 1));
        boolean result = this.s2SAuthenticationService.validateToken(token);

        assertThat(result).isFalse();
    }

    @Test(expected = SignatureException.class)
    public void validateToken_throwsException_forInValidSecretAndGoodTimeout() {
        final String token = createToken(SECRET_RULES, DEFAULT_EXPIRY, SERVICE_NAME);

        when(config.getRules())
            .thenReturn(new S2SAuthenticationConfig.JwtCredentials(SECRET_RULES + "AAAA", DEFAULT_EXPIRY));
        this.s2SAuthenticationService.validateToken(token);
    }

    @Test
    public void validateToken_returnsFalse_forMissingToken() {
        final String token = "";
        boolean result = this.s2SAuthenticationService.validateToken(token);
        assertThat(result).isFalse();
    }

    private String createToken(String secret, long expiryInMs, String serviceName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiryInMs);

        return Jwts.builder()
            .claim("service", serviceName)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
}
