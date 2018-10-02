package uk.gov.hmcts.reform.sandl.snlrules.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.sandl.snlrules.BaseIntegrationTest;
import uk.gov.hmcts.reform.sandl.snlrules.utils.JwtTokenHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { S2SAuthenticationConfig.class },
    initializers = {ConfigFileApplicationContextInitializer.class})
@DirtiesContext
public class S2SJwtAuthenticationFilterIntegrationTest extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

    @Value("${management.security.rules.jwtSecret}")
    private String jwtSecret;

    @Test
    public void getExportcounts_orAnyOther_shouldPass_WithValidS2SJwtToken() {

        HttpStatus expectedStatusCode = HttpStatus.OK;

        HttpHeaders headers = JwtTokenHelper.createRulesAuthenticationHeader(jwtSecret);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
            getTestUrl(), HttpMethod.GET, entity, String.class);

        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains("PROBLEM"));
    }

    @Test
    public void getExportcounts_orAnyOther_shouldFail_WithInvalidS2SJwtToken() {

        HttpStatus expectedStatusCode = HttpStatus.UNAUTHORIZED;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "INVALID");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
            getTestUrl(), HttpMethod.GET, entity, String.class);

        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertFalse(response.getBody().contains("PROBLEM"));
    }

    @Test
    public void anyJwtFreeEndpoint_shouldPass_WithoutS2SJwtToken() {

        HttpStatus expectedStatusCode = HttpStatus.OK;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
            getJwtFreeTestUrl(), HttpMethod.GET, entity, String.class);

        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertTrue(response.getBody().contains("Welcome"));
    }

    private String getTestUrl(String... args) {
        if (args.length == 0) {
            return "http://localhost:" + port + "/exportcounts"; // because it's simple endpoint to use
        } else {
            return "http://localhost:" + port + String.join("", args);
        }
    }

    private String getJwtFreeTestUrl() {
        return getTestUrl("/");
    }


}

