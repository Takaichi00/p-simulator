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

    Double ballAfterRate = Double.valueOf(ball) * rate;

    if (ballAfterRate >= prizeRateInformation.getSmallPrizePrice()) {
      ++smallPrizeAmount;
    }

    return Prize.builder()
                .smallPrizeAmount(smallPrizeAmount)
                .build();
  }
}
