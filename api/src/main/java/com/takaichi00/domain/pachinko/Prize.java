package com.takaichi00.domain.pachinko;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Prize {
  private final Integer smallPrizeAmount;
  private final Integer middlePrizeAmount;
  private final Integer bigPrizeAmount;
}
