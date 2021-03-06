package uk.gov.hmcts.reform.sandl.snlrules.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class S2SJwtAuthenticationFilterTest {

    private S2SAuthenticationService s2SAuthenticationService;
    private S2SJwtAuthenticationFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @Before
    public void setup() {
        s2SAuthenticationService = Mockito.mock(S2SAuthenticationService.class);
        filter = new S2SJwtAuthenticationFilter(s2SAuthenticationService);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);
    }

    @Test
    public void validateToken_respondsWithError_forNoTokenInProperHeader() throws ServletException, IOException {
        when(s2SAuthenticationService.validateToken(any())).thenReturn(false);
        when(request.getHeader(S2SAuthenticationService.HEADER_NAME))
            .thenReturn(S2SAuthenticationService.HEADER_CONTENT_PREFIX);
        when(request.getRequestURI())
            .thenReturn("/needingJwtUrl");

        filter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void validateToken_responseWithError_forInvalidTokenInProperHeader() throws ServletException, IOException {
        when(s2SAuthenticationService.validateToken(any())).thenReturn(false);
        when(request.getHeader(S2SAuthenticationService.HEADER_NAME))
            .thenReturn(S2SAuthenticationService.HEADER_CONTENT_PREFIX + "INVALIDTOKEN");
        when(request.getRequestURI())
            .thenReturn("/needingJwtUrl");

        filter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void validateToken_responseWithError_forNoBearerTextInHeader() throws ServletException, IOException {
        when(s2SAuthenticationService.validateToken(any())).thenReturn(false);
        when(request.getHeader(S2SAuthenticationService.HEADER_NAME))
            .thenReturn("NO BEARER IN HEADER");
        when(request.getRequestURI())
            .thenReturn("/needingJwtUrl");

        filter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void validateToken_responseWithError_forNoValueInHeader() throws ServletException, IOException {
        when(s2SAuthenticationService.validateToken(any())).thenReturn(false);
        when(request.getHeader(S2SAuthenticationService.HEADER_NAME))
            .thenReturn("");
        when(request.getRequestURI())
            .thenReturn("/needingJwtUrl");

        filter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(eq(HttpServletResponse.SC_UNAUTHORIZED), anyString());
    }

    @Test
    public void doFilterInternal_respondsWithOk_whenSecurityIsDisabled() throws ServletException, IOException {
        when(s2SAuthenticationService.isDisabled()).thenReturn(true);
        when(request.getRequestURI())
            .thenReturn("/needingJwtUrl");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(any(), any());
        verify(response, Mockito.times(0)).sendError(anyInt());
    }
}
