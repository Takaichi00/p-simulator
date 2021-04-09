package com.takaichi00.domain.pachinko;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PachinkoStore {

  private final PrizeRateInformation prizeRateInformation;
  private final double rate;

  public static PachinkoStore of(PrizeRateInformation prizeRateInformation, double rate) {
    return new PachinkoStore(prizeRateInformation, rate);
  }

  public Prize convertBallToPrize(int ball) {

    int smallPrizeAmount = 0;
    int middlePrizeAmount = 0;
    int bigPrizeAmount = 0;

    double ballAfterRate = (double) ball * rate;

    while (ballAfterRate >= prizeRateInformation.getBigPrizePrice()) {
      ++bigPrizeAmount;
      ballAfterRate = ballAfterRate - prizeRateInformation.getBigPrizePrice();
    }

    while (ballAfterRate >= prizeRateInformation.getMiddlePrizePrice()) {
      ++middlePrizeAmount;
      ballAfterRate = ballAfterRate - prizeRateInformation.getMiddlePrizePrice();
    }

    while (ballAfterRate >= prizeRateInformation.getSmallPrizePrice()) {
      ++smallPrizeAmount;
      ballAfterRate = ballAfterRate - prizeRateInformation.getSmallPrizePrice();
    }

    return new Prize(smallPrizeAmount, middlePrizeAmount, bigPrizeAmount);
  }
}
