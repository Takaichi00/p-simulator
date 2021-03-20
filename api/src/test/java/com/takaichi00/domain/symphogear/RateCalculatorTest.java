package com.takaichi00.domain.symphogear;

import static org.junit.jupiter.api.Assertions.*;

import com.takaichi00.domain.pachinko.RateCalculator;
import org.junit.jupiter.api.Test;

class RateCalculatorTest {

  RateCalculator testTarget;

  @Test
  void ランダムの整数が0で分子が1の場合はtrueが取得できる() {
    testTarget = new RateCalculator(new CustomRandom(0));
    Boolean expected = true;
    Boolean actual = testTarget.calculate(1, 10);
    assertEquals(expected, actual);
  }

  @Test
  void ランダムの整数が1で分子が1の場合はfalseが取得できる() {
    testTarget = new RateCalculator(new CustomRandom(1));
    Boolean expected = false;
    Boolean actual = testTarget.calculate(1, 10);
    assertEquals(expected, actual);
  }

  @Test
  void ランダムの整数が9で分子が10の場合はtrueが取得できる() {
    testTarget = new RateCalculator(new CustomRandom(9));
    Boolean expected = true;
    Boolean actual = testTarget.calculate(10, 1998);
    assertEquals(expected, actual);
  }

  @Test
  void ランダムの整数が10で分子が10の場合はfalseが取得できる() {
    testTarget = new RateCalculator(new CustomRandom(10));
    Boolean expected = false;
    Boolean actual = testTarget.calculate(10, 1998);
    assertEquals(expected, actual);
  }

  @Test
  void ランダム生成を試す学習用テスト() {
    testTarget = new RateCalculator();
    for (int i = 0; i < 200; ++i) {
      if (testTarget.calculate(10, 1990))
      System.out.println("Hit!!!");
    }
  }
}

