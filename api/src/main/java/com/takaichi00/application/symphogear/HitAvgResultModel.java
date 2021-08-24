package com.takaichi00.application.symphogear;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class HitAvgResultModel {
  private final BigDecimal investmentYen;
  private final BigDecimal collectionBall;
  private final BigDecimal collectionYen;
  private final BigDecimal balanceResultYen;
  private final BigDecimal firstHit;
  private final BigDecimal continuousHitCount;
}
