package com.takaichi00.application.symphogear;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class HitSummaryResultModel {
  private final BigDecimal investmentYenAvg;
  private final BigDecimal collectionBallAvg;
  private final BigDecimal collectionYenAvg;
  private final BigDecimal balanceResultYenAvg;
  private final BigDecimal firstHitAvg;
  private final BigDecimal continuousHitCountAvg;
}
