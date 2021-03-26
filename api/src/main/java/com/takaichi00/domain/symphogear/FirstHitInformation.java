package com.takaichi00.domain.symphogear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
public class FirstHitInformation {
  private final int firstHitMoney;
  private final int firstHitBall;
  private final int firstHitRound;

  public static FirstHitInformation of(int firstHitMoney, int firstHitBall, int firstHitRound) {
    return new FirstHitInformation(firstHitMoney, firstHitBall, firstHitRound);
  }

}
