package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SymphogearMachineTest {

  private SymphogearMachine testTarget;

  @DisplayName("共通機能")
  @Nested
  class Common {

    @Test
    void _500円を入金すると125玉を払い出す() {
      testTarget = new SymphogearMachine(new RateCalculator(new CustomRandom(100)));
      int expected = 125;
      int actual = testTarget.outBallBy500Yen();
      assertEquals(expected, actual);
    }

    @Test
    void _へその賞球として3球取得できる() {
      testTarget = new SymphogearMachine(new RateCalculator(new CustomRandom(100)));
      int expected = 3;
      int actual = testTarget.getAwardBall();
      assertEquals(expected, actual);
    }
  }

  @DisplayName("通常時")
  @Nested
  class Normal {
    @Test
    @DisplayName("1回抽選して 1/199.8 を引き当てなかった場合はハズレが取得できる")
    void _1回抽選してxxxを引き当てなかった場合はハズレが取得できる() {
      // setup
      Boolean expected = false;
      testTarget = new SymphogearMachine(new RateCalculator(new CustomRandom(100)));

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
      testTarget = new SymphogearMachine(new RateCalculator(new CustomRandom(9)));

      // execute
      Boolean actual = testTarget.drawLots();

      // assert
      assertEquals(expected, actual);
    }

    @Test
    @DisplayName("大当たりが発生すると 1/100 の確率の確率でシンフォギアチャンス GX に突入する")
    void 大当たりが発生するとxxxの確率シンフォギアチャンスGXに突入する() {
      testTarget = new SymphogearMachine(new RateCalculator(new CustomRandom(0)));

      int expected = 1300;
      int actual = testTarget.getHitRoundCount();
      assertEquals(expected, actual);

      SymphogearModeStatus expectedModeStatus = SymphogearModeStatus.CHANCE_GX_10R_103ROTATION;
      SymphogearModeStatus actualModeStatus = testTarget.getModeStatus();
      assertEquals(expectedModeStatus, actualModeStatus);
    }

    @Test
    @DisplayName("大当たりが発生すると 99/100 の確率で 3R (390の出玉)を取得でき、最終決戦に突入する")
    void 大当たりが発生するとxxxの確率で3Rを取得でき最終決戦に突入する() {
      testTarget = new SymphogearMachine(new RateCalculator(new CustomRandom(1)));

      int expectedHitRoundCount = 390;
      int actualHitRoundCount = testTarget.getHitRoundCount();

      assertEquals(expectedHitRoundCount, actualHitRoundCount);

      SymphogearModeStatus expectedModeStatus = SymphogearModeStatus.LAST_BATTLE;
      SymphogearModeStatus actualModeStatus = testTarget.getModeStatus();
      assertEquals(expectedModeStatus, actualModeStatus);
    }
  }

  @Nested
  @DisplayName("最終決戦")
  class LastBattle {

    @Test
    @DisplayName("1/7.6 の確率を5回抽選し、もし5回目で1回大当りが獲得できたらシンフォギアチャンス GX に突入する")
    void xxxの確率を5回抽選しもし1回以上大当りが獲得できたらシンフォギアチャンスGXに突入する() {

      RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
      testTarget = new SymphogearMachine(rateCalculator);

      when(rateCalculator.calculate(10,76)).thenReturn(false,
                                                                          false,
                                                                               false,
                                                                               false,
                                                                               true);

      SymphogearModeStatus expectedModeStatus = SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION;
      testTarget.lastBattle();
      SymphogearModeStatus actualModeStatus = testTarget.getModeStatus();
      assertEquals(expectedModeStatus, actualModeStatus);
    }

    @Test
    @DisplayName("1/7.6 の確率を5回抽選し、もし一回も大当りが獲得できなければ通常時に戻る")
    void xxxの確率を5回抽選しもしもし一回も大当りが獲得できなければ通常時に戻る() {

      RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
      testTarget = new SymphogearMachine(rateCalculator);

      when(rateCalculator.calculate(10,76)).thenReturn(false,
                                                                          false,
                                                                               false,
                                                                               false,
                                                                               false);

      SymphogearModeStatus expectedModeStatus = SymphogearModeStatus.NORMAL;
      testTarget.lastBattle();
      SymphogearModeStatus actualModeStatus = testTarget.getModeStatus();
      assertEquals(expectedModeStatus, actualModeStatus);
    }
  }

  @Nested
  @DisplayName("シンフォギアチャンス GX")
  class SymphogearChanceGx {

    @Nested
    @DisplayName("最終決戦もしくはシンフォギアチャンス GX からシンフォギアチャンス GX に突入した場合")
    class normalGX {

      @Test
      void ラウンド振り分けを実施し_45パーセントの確率で4R_11回転の振り分けになる() {

        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
                                                                            false,
                                                                                 false,
                                                                                 false,
                                                                                 true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(true);

        int expectedGetBall = 520;
        int expectedRotation = 11;

        testTarget.roundAllocationGx();
        int actualGetBall = testTarget.getBallByGx();
        int actualRotation = testTarget.getRotationGx();
        assertEquals(expectedGetBall, actualGetBall);
        assertEquals(expectedRotation, actualRotation);
      }

      @Test
      void ラウンド振り分けを実施し_36パーセントの確率で10R_11回転の振り分けになる() {

        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(false);
        when(rateCalculator.calculate(36,55)).thenReturn(true);

        int expectedGetBall = 1300;
        int expectedRotation = 11;

        testTarget.roundAllocationGx();
        int actualGetBall = testTarget.getBallByGx();
        int actualRotation = testTarget.getRotationGx();
        assertEquals(expectedGetBall, actualGetBall);
        assertEquals(expectedRotation, actualRotation);
      }

      @Test
      void ラウンド振り分けを実施し_11パーセントの確率で10R_103回転の振り分けになる() {

        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(false);
        when(rateCalculator.calculate(36,55)).thenReturn(false);
        when(rateCalculator.calculate(11,19)).thenReturn(true);

        int expectedGetBall = 1300;
        int expectedRotation = 103;

        testTarget.roundAllocationGx();
        int actualGetBall = testTarget.getBallByGx();
        int actualRotation = testTarget.getRotationGx();
        assertEquals(expectedGetBall, actualGetBall);
        assertEquals(expectedRotation, actualRotation);
      }

      @Test
      void ラウンド振り分けを実施し_3パーセントの確率で10R_15回転の振り分けになる() {

        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(false);
        when(rateCalculator.calculate(36,55)).thenReturn(false);
        when(rateCalculator.calculate(11,19)).thenReturn(false);
        when(rateCalculator.calculate(3,8)).thenReturn(true);

        int expectedGetBall = 1300;
        int expectedRotation = 15;

        testTarget.roundAllocationGx();
        int actualGetBall = testTarget.getBallByGx();
        int actualRotation = testTarget.getRotationGx();
        assertEquals(expectedGetBall, actualGetBall);
        assertEquals(expectedRotation, actualRotation);
      }

      @Test
      void ラウンド振り分けを実施し_3パーセントの確率で6R_11回転の振り分けになる() {

        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(false);
        when(rateCalculator.calculate(36,55)).thenReturn(false);
        when(rateCalculator.calculate(11,19)).thenReturn(false);
        when(rateCalculator.calculate(3,8)).thenReturn(false);
        when(rateCalculator.calculate(3,5)).thenReturn(true);

        int expectedGetBall = 780;
        int expectedRotation = 11;

        testTarget.roundAllocationGx();
        int actualGetBall = testTarget.getBallByGx();
        int actualRotation = testTarget.getRotationGx();
        assertEquals(expectedGetBall, actualGetBall);
        assertEquals(expectedRotation, actualRotation);
      }

      @Test
      void ラウンド振り分けを実施し_2パーセントの確率で7R_11回転の振り分けになる() {

        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(false);
        when(rateCalculator.calculate(36,55)).thenReturn(false);
        when(rateCalculator.calculate(11,19)).thenReturn(false);
        when(rateCalculator.calculate(3,8)).thenReturn(false);
        when(rateCalculator.calculate(3,5)).thenReturn(false);

        int expectedGetBall = 910;
        int expectedRotation = 11;

        testTarget.roundAllocationGx();
        int actualGetBall = testTarget.getBallByGx();
        int actualRotation = testTarget.getRotationGx();
        assertEquals(expectedGetBall, actualGetBall);
        assertEquals(expectedRotation, actualRotation);
      }

      @Test
      void ラウンド振り分け分の抽選を実施し_当選したらシンフォギアチャンスGXが継続する() {
        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
                                                                            false,
                                                                                 false,
                                                                                 false,
                                                                                 true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(true);
        testTarget.roundAllocationGx();

        when(rateCalculator.calculate(10,76)).thenReturn(false,
                                                                            false,
                                                                                 false,
                                                                                 false,
                                                                                 false,
                                                                                 false,
                                                                                 true);

        testTarget.gxBattle();

        SymphogearModeStatus actual = testTarget.getModeStatus();
        SymphogearModeStatus expected = SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION;
        assertEquals(expected, actual);

      }

      @Test
      void ラウンド振り分け分の抽選を実施し_当選しなかったら通常時に戻る() {
        RateCalculator rateCalculator = Mockito.mock(RateCalculator.class);
        testTarget = new SymphogearMachine(rateCalculator);

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            true);
        testTarget.lastBattle();

        when(rateCalculator.calculate(45,100)).thenReturn(true);
        testTarget.roundAllocationGx();

        when(rateCalculator.calculate(10,76)).thenReturn(false,
            false,
            false,
            false,
            false,
            false,
            false);

        testTarget.gxBattle();

        SymphogearModeStatus actual = testTarget.getModeStatus();
        SymphogearModeStatus expected = SymphogearModeStatus.NORMAL;
        assertEquals(expected, actual);

      }
    }
  }
}
