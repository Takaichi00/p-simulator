package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
    void Playerは0玉を所持している場合_500円を消費して125玉を取得し_1玉当たりのへそに入れる確率で1回へそに入れるまで抽選し_消費した玉を取得できる() {
      RateCalculator spyRateCalculator = spy(new RateCalculator());
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN, spyRateCalculator);
      
      when(spyRateCalculator.calculate(20, 250))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9));

      int expected = 10;
      int actual = testTarget.putBallUntilInNavel();
      assertEquals(expected, actual);

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 115;
      assertEquals(expectedHavingBall, actualHavingBall);

    }

    @Test
    void Playerは0玉所持している場合_大当りを取得するまで玉をへそに入れ続け_大当りを獲得したときの消費玉と消費金額と回転数を取得できる() {
      // 100回転で大当りを獲得する場合
      // 1回転10玉消費 → 100回転 1000玉投資 → 1000/125=8 → 8*500=4000円投資

      FirstHitInformation expected = FirstHitInformation.builder()
                                                        .firstHitBall(1000)
                                                        .firstHitMoney(4000)
                                                        .firstHitRound(100)
                                                        .build();
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false);
      FirstHitInformation actual = testTarget.playSymphogearUntilFirstHit();

      assertEquals(expected.getFirstHitBall(), actual.getFirstHitBall());
      assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
      assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_99パーセントの確率で3R_390_の出玉を取得でき_最終決戦へ突入する() {

      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      int expected = 390;
      int actual = testTarget.getHavingBall();
      assertEquals(expected, actual);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_最終決戦を実施して突破できなかった場合_遊戯をやめて保持している玉を取得できる() {
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();

      int expectedHavingBall = 390;
      PlayerStatus expectedPlayerStatus = PlayerStatus.FINISH;

      int actualHavingBall = testTarget.getHavingBall();
      PlayerStatus actualPlayerStatus = testTarget.getStatus();

      assertEquals(expectedHavingBall, actualHavingBall);
      assertEquals(expectedPlayerStatus, actualPlayerStatus);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_最終決戦を実施して突破した場合_シンフォギアチャンスGXをプレイする() {
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(true);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();

      PlayerStatus expectedPlayerStatus = PlayerStatus.PLAY_GX;
      PlayerStatus actualPlayerStatus = testTarget.getStatus();

      assertEquals(expectedPlayerStatus, actualPlayerStatus);
    }

    private SymphogearPlayer setupAndCretePlayerInstance(boolean winLastBattle) {
      // 100回転で大当りを獲得する場合
      // 1回転10玉消費 → 100回転 1000玉投資 → 1000/125=8 → 8*500=4000円投資
      RateCalculator spyRateCalculator = spy(new RateCalculator());
      SymphogearMachine symphogearMachine = spy(new SymphogearMachine());
      SymphogearPlayer testTarget = SymphogearPlayer.of(symphogearMachine,
          ROUND_PER_1000YEN,
          spyRateCalculator);

      // へそに入れる確率をモック
      List<Boolean> inNavelMockBoolean = new ArrayList<>();
      for (int i = 0; i < 8; ++i) {
        inNavelMockBoolean.add(false);
      }
      inNavelMockBoolean.add(true);

      for (int j = 0; j < 99; ++j) {
        for (int i = 0; i < 9; ++i) {
          inNavelMockBoolean.add(false);
        }
        inNavelMockBoolean.add(true);
      }

      when(spyRateCalculator.calculate(20, 250))
          .thenReturn(false, inNavelMockBoolean.toArray(new Boolean[inNavelMockBoolean.size()]));

      // 大当りの確率をモック
      when(symphogearMachine.drawLots())
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(99));

      if (!winLastBattle) {
        when(symphogearMachine.getModeStatus()).thenReturn(SymphogearModeStatus.NORMAL);
      }

      if (winLastBattle) {
        when(symphogearMachine.getModeStatus()).thenReturn(SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION);
      }

      return testTarget;

    }
  }

  private Boolean[] createFalseArrayUntilSpecifiedTrueNumber(int trueNumber) {
    List<Boolean> value = new ArrayList<>();

    for (int i = 0; i < trueNumber - 1; ++i) {
      value.add(false);
    }
    value.add(true);

    return value.toArray(new Boolean[value.size()]);
  }

}
