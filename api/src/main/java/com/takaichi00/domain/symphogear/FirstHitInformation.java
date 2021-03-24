package com.takaichi00.domain.symphogear;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class FirstHitInformation {
  private final int firstHitMoney;
  private final int firstHitRound;

  public static FirstHitInformation of(int firstHitMoney, int firstHitRound) {
    return new FirstHitInformation(firstHitMoney, firstHitRound);
  }

}