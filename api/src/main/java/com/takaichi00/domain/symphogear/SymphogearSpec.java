package com.takaichi00.domain.symphogear;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SymphogearSpec {
  private final RateCalculator rateCalculator;

  public Boolean drawLots() {
    return false;
  }
}
