package com.takaichi00.domain.symphogear;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymphogearSpecTest {

  @Test
  @DisplayName("1回抽選して 1/199.8 を引き当てなかった場合はハズレが取得できる")
  void _1回抽選してxxxを引き当てなかった場合はハズレが取得できる() {
    // setup
    Boolean expected = false;
    SymphogearSpec testTarget = new SymphogearSpec(new RateCalculator(
                                                   new CustomRandom(100)));

    // execute
    Boolean actual = testTarget.drawLots();

    // assert
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("1回抽選して 1/199.8 を引き当てた場合はあたりが取得できる")
  void _1回抽選してxxxを引き当てた場合はあたりが取得できる() {
    // setup
    Boolean expected = true;
    SymphogearSpec testTarget = new SymphogearSpec(new RateCalculator(
      new CustomRandom(9)));

    // execute
    Boolean actual = testTarget.drawLots();

    // assert
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("大当たりが発生すると 99/100 の確率で 3R (390の出玉)を取得できる")
  void 大当たりが発生するとxxxの確率で3Rを取得できる() {
    SymphogearSpec testTarget = new SymphogearSpec(new RateCalculator(
      new CustomRandom(9)));

    int expected = 390;
    int actual = testTarget.getHitRoundCount();
    assertEquals(expected, actual);
  }

}
