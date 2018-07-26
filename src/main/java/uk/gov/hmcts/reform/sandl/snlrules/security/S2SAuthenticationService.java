package uk.gov.hmcts.reform.sandl.snlrules.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
public class S2SAuthenticationService {

    static final String HEADER_NAME = "Authorization";
    static final String HEADER_CONTENT_PREFIX = "Bearer ";
    private static final Set<String> approvedServicesNames = Collections.singleton("snl-events");
    private final S2SAuthenticationConfig config;

    @Autowired
    S2SAuthenticationService(S2SAuthenticationConfig config) {
        this.config = config;
    }

    public boolean validateToken(String authToken) {
        try {
            final Claims claims = Jwts.parser()
                .setSigningKey(config.getRules().getJwtSecret())
                .parseClaimsJws(authToken)
                .getBody();
            final String serviceName = (String) claims
                .get("service");
            boolean valid = approvedServicesNames.contains(serviceName);
            long millisDifference = claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
            return valid && config.getRules().getJwtExpirationInMs() == millisDifference;
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
