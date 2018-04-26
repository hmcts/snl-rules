package uk.gov.hmcts.reform.sandl.snlrules.functional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static io.restassured.RestAssured.get;
import static org.hamcrest.core.IsEqual.equalTo;

public class HealthCheckTest {

    @Before
    public void before() {
        TestSetup.configureAppUrl();
    }

    @Test
    @Category(SmokeTest.class)
    public void healthcheck_returns_200() {
        get("/health")
            .then().statusCode(200)
            .and().body("status", equalTo("UP"));
    }
}
