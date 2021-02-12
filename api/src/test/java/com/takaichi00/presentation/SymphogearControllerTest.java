package com.takaichi00.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;


@QuarkusTest
class SymphogearControllerTest {

  @Test
  void test_1000円あたりの回転数_交換率_出玉減り率から1回の大当たり終了時点の収支が取得できる() {
    JSONObject jsonObj = new JSONObject()
                            .put("rotation_rate_per_1000yen",20)
                            .put("change_rate",3.6)
                            .put("ball_reduction_rate",0.05);


    given().when()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonObj.toString())
            .post("v1/symphogear/balance/1")
           .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body("investment_yen", equalTo(5000))
            .body("collection_ball", equalTo(2000))
            .body("collection_yen", equalTo(7200))
            .body("balance_result_yen", equalTo(2200))
            .body("first_hit", equalTo(100))
            .body("continuous_hit_count", equalTo(3))
            .body("round_allocations[0]", equalTo("4R"))
            .body("round_allocations[1]", equalTo("10R"))
            .body("round_allocations[1]", equalTo("10R"));
  }

}
