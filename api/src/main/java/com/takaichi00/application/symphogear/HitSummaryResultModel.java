package com.takaichi00.application.symphogear;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class HitSummaryResultModel {
  private final HitResultModel avgHitResultModel;
  private final HitResultModel maxHitResultModel;
  private final HitResultModel minHitResultModel;
}
