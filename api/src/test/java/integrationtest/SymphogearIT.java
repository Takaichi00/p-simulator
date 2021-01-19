package integrationtest;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

public class SymphogearIT {

  @BeforeAll
  static void beforeAll() {
    RestAssured.baseURI = System.getProperty("it.quarkus.url", "http://localhost");
    RestAssured.port = Integer.getInteger("it.quarkus.port", 8080);
    RestAssured.basePath = System.getProperty("it.quarkus.base.path", "");
  }

  @Test
  void test_1000円あたりの回転数_交換率_出玉減り率から1回の大当たり終了時点の収支が取得できる() {
    JSONObject jsonObj = new JSONObject()
                              .put("rotation_rate_per_1000yen",20)
                              .put("change_rate",3.6)
                              .put("ball_reduction_rate",0.05);


    given().when()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObj.toString())
            .post("/symphogear/balance/1")
           .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("investment_yen", matchesPattern("[1-9][0-9]*"))
            .body("collection_ball", matchesPattern("[1-9][0-9]*"))
            .body("collection_yen", matchesPattern("[1-9][0-9]*"))
            .body("balance_result_yen", matchesPattern("[1-9][0-9]*"))
            .body("first_hit", matchesPattern("[1-9][0-9]*"))
            .body("continuous_hit_count", matchesPattern("[1-9][0-9]*"))
            .body("round_allocations", hasSize(greaterThan(0))); // https://stackoverflow.com/questions/28039981/rest-assured-how-to-check-if-not-empty-array-is-returned
  }
}
