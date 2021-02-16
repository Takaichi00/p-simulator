package com.takaichi00.presentation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.takaichi00.application.symphogear.HitResultModel;
import com.takaichi00.application.symphogear.SymphogearService;
import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.MediaType;

import io.quarkus.test.junit.mockito.InjectMock;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@QuarkusTest
class SymphogearControllerTest {

  @InjectMock
  SymphogearService symphogearService;

  @Test
  void test_1000円あたりの回転数_交換率_出玉減り率から1回の大当たり終了時点の収支が取得できる() {
    JSONObject jsonObj = new JSONObject()
                            .put("rotation_rate_per_1000yen",20)
                            .put("change_rate",3.6)
                            .put("ball_reduction_rate",0.05);

    HitResultModel hitResultModel = HitResultModel.builder()
                                                  .firstHit(100)
                                                  .continuousHitCount(3)
                                                  .roundAllocations(Arrays.asList("4R", "10R", "10R"))
                                                  .build();

    when(symphogearService.getHitInformation()).thenReturn(hitResultModel);

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

    verify(symphogearService, times(1)).getHitInformation();

  }

}
