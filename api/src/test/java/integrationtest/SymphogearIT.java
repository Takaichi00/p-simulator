package integrationtest;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
            .body("investment", equalTo("hoge"))
            .body("collection_ball", equalTo("hoge"))
            .body("collection_yen", equalTo("hoge"))
            .body("balance_result", equalTo("hoge"))
            .body("first_hit", equalTo("hoge"))
            .body("continuous_hit_count", equalTo("hoge"))
            .body("round_allocations", equalTo("hoge"));
  }
}
