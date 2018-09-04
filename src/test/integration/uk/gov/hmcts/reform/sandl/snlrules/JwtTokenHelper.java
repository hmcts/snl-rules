package uk.gov.hmcts.reform.sandl.snlrules;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import uk.gov.hmcts.reform.sandl.snlrules.security.S2SAuthenticationService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class JwtTokenHelper {

    private JwtTokenHelper() {
    }

    public static HttpHeaders createRulesAuthenticationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(S2SAuthenticationService.HEADER_NAME,
            S2SAuthenticationService.HEADER_CONTENT_PREFIX
                + createToken("nieWiedzialaJak", 5000, "snl-events")
        );
        return headers;
    }

    public static String createToken(String secret, long expiryInMs, String serviceName) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(expiryInMs, ChronoUnit.MILLIS);

        return Jwts.builder()
            .claim("service", serviceName)
            .setIssuedAt(new Date(now.toEpochMilli()))
            .setExpiration(new Date(expiryDate.toEpochMilli()))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
}
