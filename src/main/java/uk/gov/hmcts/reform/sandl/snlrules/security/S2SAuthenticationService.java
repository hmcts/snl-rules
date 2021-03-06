package uk.gov.hmcts.reform.sandl.snlrules.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j
@Service
public class S2SAuthenticationService {

    public static final String HEADER_NAME = "Authorization";
    public static final String HEADER_CONTENT_PREFIX = "Bearer ";
    private static final Set<String> approvedServicesNames = Collections.singleton("snl-events");
    private final S2SAuthenticationConfig config;

    @Autowired
    S2SAuthenticationService(S2SAuthenticationConfig config) {
        this.config = config;
    }

    public boolean validateToken(String authToken) {
        if (!StringUtils.hasText(authToken)) {
            return false;
        }
        try {
            final Claims claims = Jwts.parser()
                .setSigningKey(config.getRules().getJwtSecret())
                .parseClaimsJws(authToken)
                .getBody();
            final String serviceName = (String) claims
                .get("service");
            boolean valid = approvedServicesNames.contains(serviceName);
            Date now = new Date();
            long millisDifference = now.getTime() - claims.getIssuedAt().getTime();

            boolean notExpired = millisDifference <= config.getRules().getJwtExpirationInMs();
            return valid && notExpired;
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

    public boolean isDisabled() {
        return !this.config.enabled;
    }
}
