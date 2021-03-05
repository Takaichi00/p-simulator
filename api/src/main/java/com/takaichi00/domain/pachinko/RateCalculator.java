package com.takaichi00.domain.pachinko;

import java.util.Random;

public class RateCalculator {

  private final Random random;

  public RateCalculator() {
    random = new Random();
  }

  public RateCalculator(Random originalRandom) {
    random = originalRandom;
  }

  public boolean calcurate(Integer numerator, Integer denominator) {
    int result = random.nextInt(denominator);
    return result < numerator;
  }
}
