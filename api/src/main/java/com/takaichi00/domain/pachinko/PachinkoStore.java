package com.takaichi00.domain.pachinko;

public class PachinkoStore {
  public PachinkoStore(PrizeRateInformation prizeRateInformation, Double rate) {
  }

  public static PachinkoStore of(PrizeRateInformation prizeRateInformation, Double rate) {
    return new PachinkoStore(prizeRateInformation, rate);
  }

  public Prize convertBallToPrize(int i) {
    return Prize.builder()
                .smallPrizeAmount(1)
                .build();
  }
}
