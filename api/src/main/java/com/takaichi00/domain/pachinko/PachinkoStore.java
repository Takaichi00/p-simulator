package com.takaichi00.domain.pachinko;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PachinkoStore {

  private final PrizeRateInformation prizeRateInformation;
  private final Double rate;

  public static PachinkoStore of(PrizeRateInformation prizeRateInformation, Double rate) {
    return new PachinkoStore(prizeRateInformation, rate);
  }

  public Prize convertBallToPrize(int ball) {

    int smallPrizeAmount = 0;
    int middlePrizeAmount = 0;
    int bigPrizeAmount = 0;

    double ballAfterRate = (double) ball * rate;

    if (ballAfterRate >= prizeRateInformation.getBigPrizePrice()) {
      ++bigPrizeAmount;
      ballAfterRate = ballAfterRate - prizeRateInformation.getBigPrizePrice();
    }

    if (ballAfterRate >= prizeRateInformation.getMiddlePrizePrice()) {
      ++middlePrizeAmount;
      ballAfterRate = ballAfterRate - prizeRateInformation.getMiddlePrizePrice();
    }

    if (ballAfterRate >= prizeRateInformation.getSmallPrizePrice()) {
      ++smallPrizeAmount;
    }

    return new Prize(smallPrizeAmount, middlePrizeAmount, bigPrizeAmount);
  }
}
