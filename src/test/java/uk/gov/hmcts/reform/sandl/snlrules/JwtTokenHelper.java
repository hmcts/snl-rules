package uk.gov.hmcts.reform.sandl.snlrules;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import uk.gov.hmcts.reform.sandl.snlrules.security.S2SAuthenticationService;

import java.util.Date;

public class JwtTokenHelper {
    public static HttpHeaders createRulesAuthenticationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(S2SAuthenticationService.HEADER_NAME,
            S2SAuthenticationService.HEADER_CONTENT_PREFIX +
                createToken("FakeTestSecret", 5000, "snl-events")
        );
        return headers;
    }

    public static String createToken(String secret, long expiryInMs, String serviceName) {
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
