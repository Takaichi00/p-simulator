package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;

public class SymphogearMachine {

  private final RateCalculator rateCalculator;
  private static final int NUMERATOR_SYMPHO_NORMAL = 10;
  private static final int DENOMINATOR_SYMPHO_NORMAL = 1998;
  private static final int ROUND_COUNT_3R = 390;
  private static final int ROUND_COUNT_10R = 1300;
  private static final int OUT_BALL_COUNT_BY_500YEN = 125;

  private SymphogearModeStatus symphogearModeStatus;

  SymphogearMachine() {
    rateCalculator = new RateCalculator();
    symphogearModeStatus = SymphogearModeStatus.NORMAL;
  }

  SymphogearMachine(RateCalculator rateCalculator) {
    this.rateCalculator = rateCalculator;
    symphogearModeStatus = SymphogearModeStatus.NORMAL;
  }

  public boolean drawLots() {
    return rateCalculator.calculate(NUMERATOR_SYMPHO_NORMAL, DENOMINATOR_SYMPHO_NORMAL);
  }

  public int getHitRoundCount() {
    if (rateCalculator.calculate(1, 100)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX;
      return ROUND_COUNT_10R;
    }
    symphogearModeStatus = SymphogearModeStatus.LAST_BUTTLE;
    return ROUND_COUNT_3R;
  }

  public SymphogearModeStatus getModeStatus() {
    return symphogearModeStatus;
  }

  public void lastBattle() {
    int hitCount = 0;
    for (int i = 0; i < 5; ++i) {
      if (rateCalculator.calculate(10, 76)) {
        ++hitCount;
      }
    }
    if (hitCount > 0) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX;
    } else {
      symphogearModeStatus = SymphogearModeStatus.NORMAL;
    }
  }

  public int outBallBy500Yen() {
    return OUT_BALL_COUNT_BY_500YEN;
  }
}
