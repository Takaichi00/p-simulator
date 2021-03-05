package com.takaichi00.domain.pachinko;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Prize {
  private final Integer smallPrizeAmount;
  private final Integer middlePrizeAmount;
  private final Integer BigPrizeAmount;
}
