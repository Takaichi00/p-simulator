package com.takaichi00.domain.symphogear;

import java.util.Random;

public class RateCalculator {

  private final Random random;

  public RateCalculator() {
    random = new Random();
  }

  RateCalculator(Random originalRandom) {
    random = originalRandom;
  }

  public Boolean calcurate(Integer numerator, Integer denominator) {
    int result = random.nextInt(denominator);
    return result < numerator;
  }
}
