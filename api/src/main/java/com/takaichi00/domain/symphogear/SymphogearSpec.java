package com.takaichi00.domain.symphogear;

public class SymphogearSpec {

  private final RateCalculator rateCalculator;
  private static final int NUMERATOR_SYMPHO_NORMAL = 10;
  private static final int DENOMINATOR_SYMPHO_NORMAL = 1998;
  private static final int ROUND_COUNT_3R = 390;
  private static final int ROUND_COUNT_10R = 1300;

  private SymphogearModeStatus symphogearModeStatus;

  SymphogearSpec() {
    rateCalculator = new RateCalculator();
  }

  SymphogearSpec(RateCalculator rateCalculator) {
    this.rateCalculator = rateCalculator;
    symphogearModeStatus = SymphogearModeStatus.NORMAL;
  }

  public Boolean drawLots() {
    return rateCalculator.calcurate(NUMERATOR_SYMPHO_NORMAL, DENOMINATOR_SYMPHO_NORMAL);
  }

  public int getHitRoundCount() {
    if (rateCalculator.calcurate(1, 100)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX;
      return ROUND_COUNT_10R;
    }
    symphogearModeStatus = SymphogearModeStatus.LAST_BUTTLE;
    return ROUND_COUNT_3R;
  }

  public SymphogearModeStatus getModeStatus() {
    return symphogearModeStatus;
  }

  public SymphogearModeStatus lastBattle() {
    for (int i = 0; i < 5; ++i) {
      if (rateCalculator.calcurate(10, 76)) {
        return SymphogearModeStatus.CHANCE_GX;
      }
    }
    return SymphogearModeStatus.NORMAL;
  }
}
