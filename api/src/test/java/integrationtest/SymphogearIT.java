package integrationtest;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class SymphogearIT {

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


    // TODO want to control random integer.
    given().when()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObj.toString())
            .post("v1/symphogear/balance")
           .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("investment_yen", is(greaterThan(0)))
            .body("collection_ball", is(greaterThan(0)))
            .body("collection_yen", is(greaterThan(0)))
            .body("balance_result_yen", is(greaterThan(Integer.MIN_VALUE)))
            .body("first_hit", is(greaterThan(0)))
            .body("continuous_hit_count", is(greaterThan(0)))
            .body("round_allocations", hasSize(greaterThan(0))); // https://stackoverflow.com/questions/28039981/rest-assured-how-to-check-if-not-empty-array-is-returned
  }
}
