package uk.gov.hmcts.reform.sandl.snlrules.functional;

import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestSetup {
    private static final Logger log = LoggerFactory.getLogger(TestSetup.class);

    private TestSetup() {}

    public static void configureAppUrl() {
        String appUrl = System.getenv("TEST_URL");
        if (appUrl == null) {
            appUrl = "http://localhost:8091";
        }

        RestAssured.baseURI = appUrl;
        RestAssured.useRelaxedHTTPSValidation();
        log.info("Base Url set to: " + RestAssured.baseURI);
    }
}
