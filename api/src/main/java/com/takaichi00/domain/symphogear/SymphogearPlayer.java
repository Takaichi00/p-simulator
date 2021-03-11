package com.takaichi00.domain.symphogear;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class SymphogearPlayer {

  private final int roundPer1000yen;

  public static SymphogearPlayer of(int roundPer1000yen) {
    return new SymphogearPlayer(roundPer1000yen);
  }

  public void playSymphogear() {
  }

  public FirstHitInformation getFirstImformation() {
    return FirstHitInformation.of(10000, 200);
  }
}
