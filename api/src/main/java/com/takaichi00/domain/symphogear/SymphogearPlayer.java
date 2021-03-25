package com.takaichi00.domain.symphogear;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

import com.takaichi00.domain.pachinko.RateCalculator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SymphogearPlayer {

  private final SymphogearMachine symphogearMachine;
  private final int roundPer1000yen;
  private final RateCalculator rateCalculator;

  private int havingBall = 0;
  private int useMoney = 0;

  public SymphogearPlayer(SymphogearMachine symphogearMachine, int roundPer1000yen) {
    this.symphogearMachine = symphogearMachine;
    this.roundPer1000yen = roundPer1000yen;
    this.rateCalculator = new RateCalculator();
  }

  public SymphogearPlayer(SymphogearMachine symphogearMachine,
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

  public void playSymphogear() {
  }

  public FirstHitInformation getFirstInformation() {
    int hitCount = 1;

    while (!symphogearMachine.drawLots()) {
      ++hitCount;
    }
    BigDecimal totalYen = BigDecimal.valueOf(hitCount)
        .divide(BigDecimal.valueOf(roundPer1000yen), 5, HALF_UP)
        .multiply(BigDecimal.valueOf(1000));

    return FirstHitInformation.of(totalYen.intValue(), hitCount);
  }

  public void getBallBy500Yen() {
    havingBall += symphogearMachine.outBallBy500Yen();
    useMoney += 500;
  }

  public BigDecimal getProbabilityDrawingPer1Ball() {
    int ballCountBy1000 = symphogearMachine.outBallBy500Yen() * 2;
    return BigDecimal.valueOf(roundPer1000yen)
        .divide(BigDecimal.valueOf(ballCountBy1000));
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

  public int getHavingBall() {
    return havingBall;
  }

  public int getUseMoney() {
    return useMoney;
  }
}
