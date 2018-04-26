package uk.gov.hmcts.reform.sandl.snlrules.functional;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class FactMessagesTest {

    @Before
    public void before() {
        TestSetup.configureAppUrl();
    }

    @Test
    @Category(SmokeTest.class)
    public void session_insert_generates_problem() {

        String msgDelete = "{\n"
            + "\t\"type\": \"delete-session\",\n"
            + "\t\"data\": \"{\\\"id\\\":\\\"s523123123\\\","
            + "\\\"judgeId\\\":\\\"j1\\\",\\\"start\\\":\\\"2048-04-12T15:24:05.255+01:00\\\","
            + "\\\"duration\\\": 3600}\"\n"
            + "}";

        String msgInsert = "{\n"
            + "\t\"type\": \"insert-session\",\n"
            + "\t\"data\": \"{\\\"id\\\":\\\"s523123123\\\","
            + "\\\"judgeId\\\":\\\"j1\\\",\\\"start\\\":\\\"2048-04-12T15:24:05.255+01:00\\\","
            + "\\\"duration\\\": 3600}\"\n"
            + "}";

        // ensure there is no fact with the id already
        given()
            .contentType(ContentType.JSON)
            .body(msgDelete)
            .when()
            .put("/msg")
            .then()
            .statusCode(200);

        JsonPath retrievedFactList = given()
            .contentType(ContentType.JSON)
            .body(msgInsert)
            .when()
            .put("/msg")
            .then()
            .statusCode(200)
            .and()
            .extract().jsonPath();

        assertThat(retrievedFactList.getList("type")).contains("Issue");
    }
}
