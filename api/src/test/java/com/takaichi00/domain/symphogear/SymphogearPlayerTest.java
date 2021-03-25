package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SymphogearPlayerTest {

  @Nested
  class Playerは1000円あたり20回転でシンフォチアを打つ能力をもつ場合 {

    private static final int ROUND_PER_1000YEN = 20;

    @Test
    void Playerは500円をマシンに入れると所持玉と消費金額を取得できる() {
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN);
      int expectedBall = 125;
      int expectedMoney = 500;

      testTarget.getBallBy500Yen();
      int actualBall = testTarget.getHavingBall();
      int actualMoney = testTarget.getUseMoney();

      assertEquals(expectedBall, actualBall);
      assertEquals(expectedMoney, actualMoney);
    }

    @Test
    void Playerは1000円をマシンに入れると所持玉と消費金額を取得できる() {
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN);
      int expectedBall = 250;
      int expectedMoney = 1000;

      testTarget.getBallBy500Yen();
      testTarget.getBallBy500Yen();
      int actualBall = testTarget.getHavingBall();
      int actualMoney = testTarget.getUseMoney();

      assertEquals(expectedBall, actualBall);
      assertEquals(expectedMoney, actualMoney);
    }

    @Test
    void Playerの1玉当たりへそに入れる確率を取得できる() {
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN);

      BigDecimal expected = BigDecimal.valueOf(0.08);
      BigDecimal actual = testTarget.getProbabilityDrawingPer1Ball();
      assertEquals(expected, actual);

    }

    @Test
    void Playerは0玉を所持している場合_500円を消費して250玉を取得し_1玉当たりのへそに入れる確率で1回へそに入れるまで抽選し_消費した玉を取得できる() {
      RateCalculator spyRateCalculator = spy(new RateCalculator());
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN, spyRateCalculator);

      List<Boolean> hitMockBoolean = new ArrayList<>();

      for (int i = 0; i < 8; ++i) {
        hitMockBoolean.add(false);
      }
      hitMockBoolean.add(true);

      when(spyRateCalculator.calculate(20, 250))
          .thenReturn(false, hitMockBoolean.toArray(new Boolean[hitMockBoolean.size()]));

      int expected = 10;
      int actual = testTarget.putBallUntilInNavel();
      assertEquals(expected, actual);

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 115;
      assertEquals(expectedHavingBall, actualHavingBall);

    }

    @Nested
    class 通常時のシンフォギアを大当りするまで打ち_初当たりにかかったお金と回転数を取得できる {

      @ParameterizedTest
      @CsvSource({
        "10000, 200",
        "7500, 150",
        "5000, 100",
        "2500, 50",
        "100, 2",
      })
      void X円投資Y回転であたった場合(int firstHitMoney, int firstHitRound) {
        // setup
        SymphogearMachine symphogearMachine = spy(new SymphogearMachine());

        List<Boolean> hitMockBoolean = new ArrayList<>();

        for (int i = 0; i < firstHitRound - 2; ++i) {
          hitMockBoolean.add(false);
        }
        hitMockBoolean.add(true);

        when(symphogearMachine.drawLots()).thenReturn(false, hitMockBoolean.toArray(new Boolean[hitMockBoolean.size()]));

        SymphogearPlayer testTarget = SymphogearPlayer.of(symphogearMachine, ROUND_PER_1000YEN);
        FirstHitInformation expected = FirstHitInformation.of(firstHitMoney, firstHitRound);

        // execute
        testTarget.playSymphogear();
        FirstHitInformation actual = testTarget.getFirstInformation();

        // assert
        assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
        assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
      }

      @Test
      void _50円投資1回転であたった場合() {
        // setup
        SymphogearMachine symphogearMachine = spy(new SymphogearMachine());


        when(symphogearMachine.drawLots()).thenReturn(true);

        SymphogearPlayer testTarget = SymphogearPlayer.of(symphogearMachine, ROUND_PER_1000YEN);
        FirstHitInformation expected = FirstHitInformation.of(50, 1);

        // execute
        testTarget.playSymphogear();
        FirstHitInformation actual = testTarget.getFirstInformation();

        // assert
        assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
        assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
      }
    }
  }
}
