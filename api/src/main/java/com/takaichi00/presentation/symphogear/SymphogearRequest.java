package com.takaichi00.presentation.symphogear;

import java.math.BigDecimal;
import javax.json.bind.annotation.JsonbProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SymphogearRequest {
  @JsonbProperty("rotation_rate_per_1000yen")
  private Integer rotationRatePer1000yen;

  @JsonbProperty("change_rate")
  private BigDecimal change_rate;

  @JsonbProperty("ball_reduction_rate")
  private BigDecimal ballReductionRate;

}
