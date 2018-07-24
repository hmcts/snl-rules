package uk.gov.hmcts.reform.sandl.snlrules.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
public class S2SAuthenticationService {

    static final String HEADER_NAME = "Authorization";
    static final String HEADER_CONTENT_PREFIX = "Bearer ";
    private static final Set<String> approvedServicesNames = Collections.singleton("snl-events");
    private final String localJwtSecret;
    private final int localJwtExpirationInMs;

    public S2SAuthenticationService(
        @Value("${management.security.rules.jwtSecret}") final String localJwtSecret,
        @Value("${management.security.rules.jwtExpirationInMs}") final int localJwtExpirationInMs
    ) {
        this.localJwtSecret = localJwtSecret;
        this.localJwtExpirationInMs = localJwtExpirationInMs;
    }

    public boolean validateToken(String authToken) {
        try {
            final Claims claims = Jwts.parser()
                .setSigningKey(localJwtSecret)
                .parseClaimsJws(authToken)
                .getBody();
            final String serviceName = (String) claims
                .get("service");
            boolean valid = approvedServicesNames.contains(serviceName);
            long millisDifference = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
            return valid && localJwtExpirationInMs == millisDifference;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.", ex);
        }
        return false;
    }
}
