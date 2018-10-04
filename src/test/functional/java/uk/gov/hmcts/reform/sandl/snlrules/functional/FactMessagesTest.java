package uk.gov.hmcts.reform.sandl.snlrules.functional;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.gov.hmcts.reform.sandl.snlrules.security.S2SAuthenticationConfig;
import uk.gov.hmcts.reform.sandl.snlrules.utils.JwtTokenHelper;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AnyOf.anyOf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { S2SAuthenticationConfig.class },
    initializers = {ConfigFileApplicationContextInitializer.class})
@DirtiesContext
public class FactMessagesTest {

    @Value("${management.security.rules.jwtSecret}")
    private String jwtSecret;

    @Before
    public void before() {
        TestSetup.configureAppUrl();
    }

    @Test
    @Ignore
    public void session_insert_generates_problem() {

        String sessionId = "486ecc4c-61e6-4f79-bd5d-1eb71d999316";
        String judgeId = "14c8c596-c3aa-4691-87f6-2293f07faf05";

        final String msgSessionDelete = "{\n"
            + "\t\"type\": \"delete-session\",\n"
            + "\t\"data\": \"{\\\"id\\\":\\\"" + sessionId + "\\\","
            + "\\\"judgeId\\\":\\\"" + judgeId + "\\\",\\\"start\\\":\\\"2048-04-12T15:24:05.255+01:00\\\","
            + "\\\"duration\\\": 3600}\"\n"
            + "}";

        final String msgSessionInsert = "{\n"
            + "\t\"type\": \"insert-session\",\n"
            + "\t\"data\": \"{"
            + "\\\"id\\\":\\\"" + sessionId + "\\\","
            + "\\\"judgeId\\\":\\\"" + judgeId + "\\\","
            + "\\\"start\\\":\\\"2048-04-12T15:24:05.255+01:00\\\","
            + "\\\"duration\\\": 3600}\"\n"
            + "}";

        // ensure there is no fact with the id already
        given()
            .headers(JwtTokenHelper.createRulesAuthenticationHeader(jwtSecret).toSingleValueMap())
            .contentType(ContentType.JSON)
            .body(msgSessionDelete)
            .when()
            .post("/msg?rulesDefinition=Sessions")
            .then()
            .statusCode(anyOf(is(Response.Status.NOT_FOUND.getStatusCode()), is(Response.Status.OK.getStatusCode())));

        // this should trigger "Session Time For the Judge not Available" rule
        JsonPath retrievedFactList = given()
            .headers(JwtTokenHelper.createRulesAuthenticationHeader(jwtSecret).toSingleValueMap())
            .contentType(ContentType.JSON)
            .body(msgSessionInsert)
            .when()
            .post("/msg?rulesDefinition=Sessions")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .and()
            .extract().jsonPath();

        assertThat(retrievedFactList.getList("type")).contains("Session");
    }
}
