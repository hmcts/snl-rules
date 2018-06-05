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

        String sessionId = "486ecc4c-61e6-4f79-bd5d-1eb71d999316";
        String judgeId = "14c8c596-c3aa-4691-87f6-2293f07faf05";

        final String msgSessionDelete = "{\n"
            + "\t\"type\": \"delete-session\",\n"
            + "\t\"data\": \"{\\\"id\\\":\\\"" + sessionId + "\\\","
            + "\\\"judgeId\\\":\\\"" + judgeId + "\\\",\\\"start\\\":\\\"2048-04-12T15:24:05.255+01:00\\\","
            + "\\\"duration\\\": 3600}\"\n"
            + "}";

        final String msgJudgeDelete = "{\n"
            + "\t\"type\": \"delete-judge\",\n"
            + "\t\"data\": \"{"
            + "\\\"id\\\":\\\"" + judgeId + "\\\","
            + "\\\"name\\\":\\\"John Smith\\\"}\"\n"
            + "}";

        final String msgSessionInsert = "{\n"
            + "\t\"type\": \"insert-session\",\n"
            + "\t\"data\": \"{"
            + "\\\"id\\\":\\\"" + sessionId + "\\\","
            + "\\\"judgeId\\\":\\\"" + judgeId + "\\\","
            + "\\\"start\\\":\\\"2048-04-12T15:24:05.255+01:00\\\","
            + "\\\"duration\\\": 3600}\"\n"
            + "}";

        final String  msgJudgeInsert = "{\n"
            + "\t\"type\": \"insert-judge\",\n"
            + "\t\"data\": \"{"
            + "\\\"id\\\":\\\"" + judgeId + "\\\","
            + "\\\"name\\\":\\\"John Smith\\\"}\"\n"
            + "}";

        // ensure there is no fact with the id already
        given()
            .contentType(ContentType.JSON)
            .body(msgSessionDelete)
            .when()
            .post("/msg?rulesDefinition=Sessions")
            .then()
            .statusCode(200);

        given()
            .contentType(ContentType.JSON)
            .body(msgJudgeDelete)
            .when()
            .post("/msg?rulesDefinition=Sessions")
            .then()
            .statusCode(200);

        // this should trigger "Session Time For the Judge not Available" rule
        JsonPath retrievedFactList = given()
            .contentType(ContentType.JSON)
            .body(msgSessionInsert)
            .when()
            .post("/msg?rulesDefinition=Sessions")
            .then()
            .statusCode(200)
            .and()
            .extract().jsonPath();

        assertThat(retrievedFactList.getList("type")).contains("Session");

        retrievedFactList = given()
            .contentType(ContentType.JSON)
            .body(msgJudgeInsert)
            .when()
            .post("/msg?rulesDefinition=Sessions")
            .then()
            .statusCode(200)
            .and()
            .extract().jsonPath();

        System.out.println("======================= " + retrievedFactList.toString());
        assertThat(retrievedFactList.getList("type").size()).isEqualTo(2);
        assertThat(retrievedFactList.getList("type")).contains("Problem");
        assertThat(retrievedFactList.getList("type")).contains("Judge");
    }
}
