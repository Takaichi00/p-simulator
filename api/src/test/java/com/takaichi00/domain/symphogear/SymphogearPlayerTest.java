package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SymphogearPlayerTest {

  private RateCalculator spyRateCalculator;
  private SymphogearMachine spySymphogearMachine;

  @BeforeEach
  void setup() {
    spyRateCalculator = spy(new RateCalculator());
    spySymphogearMachine = spy(new SymphogearMachine(spyRateCalculator));
  }

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
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN, spyRateCalculator);
      
      when(spyRateCalculator.calculate(20, 250))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9));

      int expected = 10;
      int actual = testTarget.putBallUntilInNavel();
      assertEquals(expected, actual);

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 118;
      assertEquals(expectedHavingBall, actualHavingBall);
    }

    @Test
    void Playerは0玉を所持している場合_500円を消費して125玉を取得し_1玉当たりのへそに入れる確率で1回へそに入れるまで抽選し_消費した玉を取得できる_2回転の場合() {
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN, spyRateCalculator);

      when(spyRateCalculator.calculate(20, 250))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9));

      int expected = 20;
      int actual1 = testTarget.putBallUntilInNavel();
      int actual2 = testTarget.putBallUntilInNavel();
      assertEquals(expected, actual1 + actual2);

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 111;
      assertEquals(expectedHavingBall, actualHavingBall);
    }

    @Test
    void Playerは0玉を所持している場合_500円を消費して125玉を取得し_1玉当たりのへそに入れる確率で1回へそに入れるまで抽選し_消費した玉を取得できる_3回転の場合() {
      SymphogearPlayer testTarget = SymphogearPlayer.of(new SymphogearMachine(), ROUND_PER_1000YEN, spyRateCalculator);

      when(spyRateCalculator.calculate(20, 250))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9))
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(9));

      int expected = 30;
      int actual1 = testTarget.putBallUntilInNavel();
      int actual2 = testTarget.putBallUntilInNavel();
      int actual3 = testTarget.putBallUntilInNavel();
      assertEquals(expected, actual1 + actual2 + actual3);

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 104;
      assertEquals(expectedHavingBall, actualHavingBall);
    }

    @Test
    void Playerは0玉所持している場合_100回転で大当たりする場合_大当りを取得するまで玉をへそに入れ続け_大当りを獲得したときの消費玉と消費金額と回転数を取得できる() {
      // 100回転で大当りを獲得する場合
      // 1回転10玉消費 → 100回転 1000玉投資 + 300賞球回収 → (1000-300)/125=5.6 → 6*500=3000円投資

      FirstHitInformation expected = FirstHitInformation.builder()
                                                        .firstHitBall(1000)
                                                        .firstHitMoney(3000)
                                                        .firstHitRound(100)
                                                        .build();
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, false);
      FirstHitInformation actual = testTarget.playSymphogearUntilFirstHit();

      assertEquals(expected.getFirstHitBall(), actual.getFirstHitBall());
      assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
      assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_99パーセントの確率で3R_390_の出玉を取得でき_最終決戦へ突入する() {

      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, false);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      int expected = 390 + 50; // 賞球を加味すると700玉で100回転 → 125 * 6 = 750 なので、50球余る
      int actual = testTarget.getHavingBall();
      assertEquals(expected, actual);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_最終決戦を実施して突破できなかった場合_遊戯をやめて保持している玉を取得できる() {
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, false);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();

      int expectedHavingBall = 440; // 390 + あまり玉50
      PlayerStatus expectedPlayerStatus = PlayerStatus.FINISH;

      int actualHavingBall = testTarget.getHavingBall();
      PlayerStatus actualPlayerStatus = testTarget.getStatus();

      assertEquals(expectedHavingBall, actualHavingBall);
      assertEquals(expectedPlayerStatus, actualPlayerStatus);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_最終決戦を実施して突破した場合_シンフォギアチャンスGXをプレイし_振り分けを実施し_取得した4Rをプレイし_保持玉910を持っている() {
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, true);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();
      testTarget.playRoundAllocationAndRound();

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 960;
      assertEquals(expectedHavingBall, actualHavingBall);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_最終決戦を実施して突破した場合_シンフォギアチャンスGXをプレイし_振り分けを実施し_取得した4Rをプレイし_振り分けラウンドを実施し_GXで一つも当たらなかった場合はゲームを終了する() {
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, true);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();
      testTarget.playRoundAllocationAndRound();

      // GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, false);

      testTarget.playGx();

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 960;
      assertEquals(expectedHavingBall, actualHavingBall);

      PlayerStatus expectedStatus = PlayerStatus.FINISH;
      PlayerStatus actualStatus = testTarget.getStatus();
      assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_最終決戦を実施して突破した場合_シンフォギアチャンスGXをプレイし_振り分けを実施し_取得した4Rをプレイし_振り分けラウンドを実施し_一つあたった場合はGXを継続し_GXで一つも当たらなかった場合はゲームを終了する() {
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, true);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();
      testTarget.playRoundAllocationAndRound();

      // 1戦目GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, true);

      testTarget.playGx();

      // 4R振り分け
      when(spyRateCalculator.calculate(45, 100)).thenReturn(true);
      testTarget.playRoundAllocationAndRound();

      // 2戦目 GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, false);
      // 2戦目 GX
      testTarget.playGx();

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 1480; // 390 (3R) + 520 (4R) + 520 (4R) + あまり玉50
      assertEquals(expectedHavingBall, actualHavingBall);

      PlayerStatus expectedStatus = PlayerStatus.FINISH;
      PlayerStatus actualStatus = testTarget.getStatus();
      assertEquals(expectedStatus, actualStatus);

      int expectedContinuousCount = 3;
      int actualContinuousCount = testTarget.getContinuousCount();
      assertEquals(expectedContinuousCount, actualContinuousCount);
    }

    @Test
    void Playerは大当り取得後_出玉減り率が0の場合_10R直撃した場合は_シンフォギアチャンスGXをプレイする() {
      // TODO
      SymphogearPlayer testTarget = setupAndCretePlayerInstance(true, true);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      // 通常時ラウンド振り分けをモック
      when(spyRateCalculator.calculate(1, 100)).thenReturn(true);

      testTarget.playRoundAllocationAndRound();

      // 1戦目GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(true);
      testTarget.playGx();

      // 4R振り分け
      when(spyRateCalculator.calculate(45, 100)).thenReturn(true);
      testTarget.playRoundAllocationAndRound();

      // 2戦目 GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, false);
      // 2戦目 GX
      testTarget.playGx();

      int actualHavingBall = testTarget.getHavingBall();
      int expectedHavingBall = 1870; // 1300 (10R) + 520 (4R) + あまり玉50
      assertEquals(expectedHavingBall, actualHavingBall);

      PlayerStatus expectedStatus = PlayerStatus.FINISH;
      PlayerStatus actualStatus = testTarget.getStatus();
      assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void PlayerはPlay終了後_大当たりの履歴を取得できる() {

      SymphogearPlayer testTarget = setupAndCretePlayerInstance(false, true);

      testTarget.playSymphogearUntilFirstHit();
      testTarget.playGetRoundAfterFirstHit();

      testTarget.playLastBattle();
      testTarget.playRoundAllocationAndRound();

      // 1戦目 GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, true);
      // 1戦目 GX
      testTarget.playGx();

      // 6R 振り分け
      when(spyRateCalculator.calculate(45, 100)).thenReturn(false);
      when(spyRateCalculator.calculate(36, 55)).thenReturn(false);
      when(spyRateCalculator.calculate(11, 19)).thenReturn(false);
      when(spyRateCalculator.calculate(3, 8)).thenReturn(false);
      when(spyRateCalculator.calculate(3, 5)).thenReturn(true);
      testTarget.playRoundAllocationAndRound();



      // 2戦目 GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, true);
      // 2戦目 GX
      testTarget.playGx();

      // 7R 振り分け
      when(spyRateCalculator.calculate(45, 100)).thenReturn(false);
      when(spyRateCalculator.calculate(36, 55)).thenReturn(false);
      when(spyRateCalculator.calculate(11, 19)).thenReturn(false);
      when(spyRateCalculator.calculate(3, 8)).thenReturn(false);
      when(spyRateCalculator.calculate(3, 5)).thenReturn(false);
      testTarget.playRoundAllocationAndRound();



      // 3戦目 GX をモック
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, true);
      // 2戦目 GX
      testTarget.playGx();

      // 10R 振り分け
      when(spyRateCalculator.calculate(45, 100)).thenReturn(false);
      when(spyRateCalculator.calculate(36, 55)).thenReturn(true);
      testTarget.playRoundAllocationAndRound();


      // 4戦目 GX をモック (負け)
      when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false, false, false);
      // 4戦目 GX
      testTarget.playGx();

      List<String> expected = Arrays.asList("3R", "4R", "6R", "7R", "10R");
      List<String> actual = testTarget.getRoundHistory();
      assertEquals(expected, actual);
    }

    private SymphogearPlayer setupAndCretePlayerInstance(boolean allRotation, boolean winLastBattle) {
      // 100回転で大当りを獲得する場合
      // 1回転10玉消費 → 100回転 1000玉投資 → 1000/125=8 → 8*500=4000円投資
      SymphogearPlayer testTarget = SymphogearPlayer.of(spySymphogearMachine,
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
      when(spySymphogearMachine.drawLots())
          .thenReturn(false, createFalseArrayUntilSpecifiedTrueNumber(99));

      // 通常時ラウンド振り分けをモック
      if (allRotation) {
        when(spyRateCalculator.calculate(1, 100)).thenReturn(true);
      } else {
        when(spyRateCalculator.calculate(1, 100)).thenReturn(false);
      }


      // 最終決戦をモック
      if (winLastBattle) {
        when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, true);
      } else {
        when(spyRateCalculator.calculate(10, 76)).thenReturn(false, false, false, false, false);
      }

      // 振り分けをモック
      when(spyRateCalculator.calculate(45, 100)).thenReturn(true);

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
