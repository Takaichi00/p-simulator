package com.takaichi00.domain.pachinko;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConversionCenter {

  private final PrizeRateInformation prizeRateInformation;

  public int convertPrizeToYen(Prize prize) {
    int sumYen = 0;
    sumYen += prizeRateInformation.getSmallPrizePrice() * prize.getSmallPrizeAmount();
    sumYen += prizeRateInformation.getMiddlePrizePrice() * prize.getMiddlePrizeAmount();
    sumYen += prizeRateInformation.getBigPrizePrice() * prize.getBigPrizeAmount();
    return sumYen;
  }
}
