package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;

public class SymphogearMachine {

  private static final int NUMERATOR_SYMPHO_NORMAL = 10;
  private static final int DENOMINATOR_SYMPHO_NORMAL = 1998;
  private static final int ROUND_COUNT_3R = 390;
  private static final int ROUND_COUNT_10R = 1300;
  private static final int OUT_BALL_COUNT_BY_500YEN = 125;

  private final RateCalculator rateCalculator;
  private SymphogearModeStatus symphogearModeStatus;

  public SymphogearMachine() {
    rateCalculator = new RateCalculator();
    symphogearModeStatus = SymphogearModeStatus.NORMAL;
  }

  public SymphogearMachine(RateCalculator rateCalculator) {
    this.rateCalculator = rateCalculator;
    symphogearModeStatus = SymphogearModeStatus.NORMAL;
  }

  public boolean drawLots() {
    return rateCalculator.calculate(NUMERATOR_SYMPHO_NORMAL, DENOMINATOR_SYMPHO_NORMAL);
  }

  public int getHitRoundCount() {
    if (rateCalculator.calculate(1, 100)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_10R_99ROTATION;
      return ROUND_COUNT_10R;
    }
    symphogearModeStatus = SymphogearModeStatus.LAST_BATTLE;
    return ROUND_COUNT_3R;
  }

  public void lastBattle() {
    int hitCount = 0;
    for (int i = 0; i < 5; ++i) {
      if (rateCalculator.calculate(10, 76)) {
        ++hitCount;
      }
    }
    if (hitCount > 0) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION;
    } else {
      symphogearModeStatus = SymphogearModeStatus.NORMAL;
    }
  }

  public int outBallBy500Yen() {
    return OUT_BALL_COUNT_BY_500YEN;
  }

  public void roundAllocationGx() {
    if (!SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION.equals(symphogearModeStatus)) {
      throw new RuntimeException("is not status CHANCE_GX_BEFORE_ALLOCATION");
    }
    if (rateCalculator.calculate(45, 100)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_4R_7ROTATION;
      return;
    }
    if (rateCalculator.calculate(36, 55)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_10R_7ROTATION;
      return;
    }
    if (rateCalculator.calculate(11, 19)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_10R_99ROTATION;
      return;
    }
    if (rateCalculator.calculate(3, 8)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_10R_11ROTATION;
      return;
    }
    if (rateCalculator.calculate(3, 5)) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_6R_7ROTATION;
      return;
    }
    symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_7R_7ROTATION;
  }

  public int getBallByGx() {
    if (SymphogearModeStatus.CHANCE_GX_4R_7ROTATION.equals(symphogearModeStatus)) {
      return 520;
    }
    if (SymphogearModeStatus.CHANCE_GX_10R_7ROTATION.equals(symphogearModeStatus)) {
      return 1300;
    }
    if (SymphogearModeStatus.CHANCE_GX_10R_99ROTATION.equals(symphogearModeStatus)) {
      return 1300;
    }
    if (SymphogearModeStatus.CHANCE_GX_10R_11ROTATION.equals(symphogearModeStatus)) {
      return 1300;
    }
    if (SymphogearModeStatus.CHANCE_GX_6R_7ROTATION.equals(symphogearModeStatus)) {
      return 780;
    }
    if (SymphogearModeStatus.CHANCE_GX_7R_7ROTATION.equals(symphogearModeStatus)) {
      return 910;
    }
    throw new RuntimeException("not GX status");
  }

  public int getRotationGx() {
    if (SymphogearModeStatus.CHANCE_GX_10R_99ROTATION.equals(symphogearModeStatus)) {
      return 99;
    }
    if (SymphogearModeStatus.CHANCE_GX_10R_11ROTATION.equals(symphogearModeStatus)) {
      return 11;
    }
    return 7;
  }

  public void gxBattle() {
    int hitCount = 0;
    for (int i = 0; i < getRotationGx(); ++i) {
      if (rateCalculator.calculate(10, 76)) {
        ++hitCount;
      }
    }
    if (hitCount > 0) {
      symphogearModeStatus = SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION;
    } else {
      symphogearModeStatus = SymphogearModeStatus.NORMAL;
    }
  }

  public SymphogearModeStatus getModeStatus() {
    return symphogearModeStatus;
  }
}
