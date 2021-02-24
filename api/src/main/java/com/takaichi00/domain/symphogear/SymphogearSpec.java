package com.takaichi00.domain.symphogear;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SymphogearSpec {

  private final RateCalculator rateCalculator;
  private static final int NUMERATOR_SYMPHO_NORMAL = 10;
  private static final int DENOMINATOR_SYMPHO_NORMAL = 1998;
  private static final int ROUND_COUNT_3R = 390;
  private static final int ROUND_COUNT_10R = 1300;

  public Boolean drawLots() {
    return rateCalculator.calcurate(NUMERATOR_SYMPHO_NORMAL, DENOMINATOR_SYMPHO_NORMAL);
  }

  public int getHitRoundCount() {
    if (rateCalculator.calcurate(1, 100)) {
      return ROUND_COUNT_10R;
    }
    return ROUND_COUNT_3R;
  }
}
