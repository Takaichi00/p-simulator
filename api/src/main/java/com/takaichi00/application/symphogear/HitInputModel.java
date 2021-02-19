package com.takaichi00.application.symphogear;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class HitInputModel {
  private final Integer rotationRatePer1000yen;
  private final BigDecimal changeRate;
  private final BigDecimal ballReductionRate;
}
