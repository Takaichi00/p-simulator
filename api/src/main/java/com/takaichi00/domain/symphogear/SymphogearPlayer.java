package com.takaichi00.domain.symphogear;

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

    int totalYen = hitCount / roundPer1000yen * 1000;

    return FirstHitInformation.of(totalYen, hitCount);
  }
}
