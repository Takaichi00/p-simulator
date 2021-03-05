package com.takaichi00.domain.pachinko;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrizeRateInformation {
  private final Integer smallPrizePrice;
  private final Integer middlePrizePrice;
  private final Integer bigPrizePrice;
}
