package com.takaichi00.domain.pachinko;

import java.security.SecureRandom;
import java.util.Random;

public class RateCalculator {

  private final Random random;

  public RateCalculator() {
    random = new SecureRandom();
  }

  public RateCalculator(Random originalRandom) {
    random = originalRandom;
  }

  public boolean calculate(Integer numerator, Integer denominator) {
    int result = random.nextInt(denominator);
    return result < numerator;
  }
}
