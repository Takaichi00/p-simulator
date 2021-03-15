package com.takaichi00.domain.symphogear;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class SymphogearPlayer {

  private final SymphogearMachine symphogearMachine;
  private final int roundPer1000yen;

  public static SymphogearPlayer of(SymphogearMachine symphogearMachine, int roundPer1000yen) {
    return new SymphogearPlayer(symphogearMachine, roundPer1000yen);
  }

  public void playSymphogear() {
  }

  public FirstHitInformation getFirstImformation() {
    int hitCount = 1;

    while (!symphogearMachine.drawLots()) {
      ++hitCount;
    }
    BigDecimal totalYen = BigDecimal.valueOf(hitCount)
        .divide(BigDecimal.valueOf(roundPer1000yen), 5, HALF_UP)
        .multiply(BigDecimal.valueOf(1000));

    return FirstHitInformation.of(totalYen.intValue(), hitCount);
  }

  public void getBallBy500Yen() {
  }

  public int getHavingBall() {
    return 125;
  }

  public int getUseMoney() {
    return 500;
  }
}
