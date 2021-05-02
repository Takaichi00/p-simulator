package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.RateCalculator;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SymphogearPlayer {

  private final SymphogearMachine symphogearMachine;
  private final int roundPer1000yen;
  private final RateCalculator rateCalculator;

  private int havingBall = 0;
  private int useMoney = 0;
  private PlayerStatus playerStatus = PlayerStatus.BEFORE_PLAY;
  private final List<String> roundHistory = new ArrayList<>();

  private SymphogearPlayer(SymphogearMachine symphogearMachine, int roundPer1000yen) {
    this.symphogearMachine = symphogearMachine;
    this.roundPer1000yen = roundPer1000yen;
    this.rateCalculator = new RateCalculator();
  }

  private SymphogearPlayer(SymphogearMachine symphogearMachine,
                          int roundPer1000yen,
                          RateCalculator rateCalculator) {
    this.symphogearMachine = symphogearMachine;
    this.roundPer1000yen = roundPer1000yen;
    this.rateCalculator = rateCalculator;
  }

  public static SymphogearPlayer of(SymphogearMachine symphogearMachine, int roundPer1000yen) {
    return new SymphogearPlayer(symphogearMachine, roundPer1000yen);
  }

  public static SymphogearPlayer of(SymphogearMachine symphogearMachine,
                                    int roundPer1000yen,
                                    RateCalculator rateCalculator) {
    return new SymphogearPlayer(symphogearMachine, roundPer1000yen, rateCalculator);
  }

  public void getBallBy500Yen() {
    havingBall += symphogearMachine.outBallBy500Yen();
    useMoney += 500;
  }

  public int putBallUntilInNavel() {
    int consumeBallUntilInNavel = 0;
    do {
      if (havingBall == 0) {
        getBallBy500Yen();
      }
      ++consumeBallUntilInNavel;
      --havingBall;
    } while (!rateCalculator.calculate(roundPer1000yen,
                           symphogearMachine.outBallBy500Yen() * 2));

    return consumeBallUntilInNavel;
  }


  public FirstHitInformation playSymphogearUntilFirstHit() {

    playerStatus = PlayerStatus.PLAYING;

    int consumeBallUntilHit = 0;
    int round = 0;

    do {
      consumeBallUntilHit += putBallUntilInNavel();
      ++round;
    } while (!symphogearMachine.drawLots());

    return FirstHitInformation.builder()
                              .firstHitBall(consumeBallUntilHit)
                              .firstHitMoney(useMoney)
                              .firstHitRound(round)
                              .build();
  }

  public void playGetRoundAfterFirstHit() {
    havingBall += symphogearMachine.getHitRoundCount();
    // TODO 10Rと3Rで以降の処理を変える
    if (SymphogearModeStatus.LAST_BATTLE.equals(symphogearMachine.getModeStatus())) {
      roundHistory.add("3R");
    } else {
      roundHistory.add("10R");
    }
  }


  public void playLastBattle() {
    symphogearMachine.lastBattle();
    SymphogearModeStatus lastBattleResult = symphogearMachine.getModeStatus();
    if (SymphogearModeStatus.NORMAL.equals(lastBattleResult)) {
      playerStatus = PlayerStatus.FINISH;
    }
    if (SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION.equals(lastBattleResult)) {
      playerStatus = PlayerStatus.PLAY_GX_ALLOCATIOM_AND_ROUND;
    }
  }


  public void playRoundAllocationAndRound() {
    if (!PlayerStatus.PLAY_GX_ALLOCATIOM_AND_ROUND.equals(playerStatus)) {
      throw new RuntimeException(
          "player's status is not PLAY_PLAY_GX_ROUND. status is " + playerStatus);
    }
    symphogearMachine.roundAllocationGx();

    if (SymphogearModeStatus.CHANCE_GX_4R_7ROTATION.equals(symphogearMachine.getModeStatus())) {
      roundHistory.add("4R");
    } else if (SymphogearModeStatus.CHANCE_GX_6R_7ROTATION
        .equals(symphogearMachine.getModeStatus())) {
      roundHistory.add("6R");
    } else if (SymphogearModeStatus.CHANCE_GX_7R_7ROTATION
        .equals(symphogearMachine.getModeStatus())) {
      roundHistory.add("7R");
    } else {
      roundHistory.add("10R");
    }

    havingBall += symphogearMachine.getBallByGx();
    playerStatus = PlayerStatus.PLAY_GX;
  }

  public void playGx() {
    if (!PlayerStatus.PLAY_GX.equals(playerStatus)) {
      throw new RuntimeException("player's status is not PLAY_GX. status is " + playerStatus);
    }
    symphogearMachine.gxBattle();
    if (SymphogearModeStatus.NORMAL.equals(symphogearMachine.getModeStatus())) {
      playerStatus = PlayerStatus.FINISH;
      return;
    }
    if (SymphogearModeStatus.CHANCE_GX_BEFORE_ALLOCATION
        .equals(symphogearMachine.getModeStatus())) {
      playerStatus = PlayerStatus.PLAY_GX_ALLOCATIOM_AND_ROUND;
      return;
    }
    throw new RuntimeException("unexpected error has occurred");
  }

  public int getHavingBall() {
    return havingBall;
  }

  public int getUseMoney() {
    return useMoney;
  }

  public PlayerStatus getStatus() {
    return playerStatus;
  }

  public List<String> getRoundHistory() {
    return roundHistory;
  }
}
