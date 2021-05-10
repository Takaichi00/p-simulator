package com.takaichi00.application.symphogear;

import com.takaichi00.domain.pachinko.ConversionCenter;
import com.takaichi00.domain.pachinko.PachinkoStore;
import com.takaichi00.domain.pachinko.Prize;
import com.takaichi00.domain.pachinko.PrizeRateInformation;
import com.takaichi00.domain.symphogear.FirstHitInformation;
import com.takaichi00.domain.symphogear.PachinkoPlayerCreator;
import com.takaichi00.domain.symphogear.PlayerStatus;
import com.takaichi00.domain.symphogear.SymphogearPlayer;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SymphogearServiceImpl implements SymphogearService {

  @Inject
  PachinkoPlayerCreator pachinkoPlayerCreator;

  @Override
  public HitResultModel getHitInformation(HitInputModel hitInputModel) {

    SymphogearPlayer symphogearPlayer
        = pachinkoPlayerCreator.createPlayer(hitInputModel.getRotationRatePer1000yen());

    FirstHitInformation firstHitInformation = symphogearPlayer.playSymphogearUntilFirstHit();
    symphogearPlayer.playGetRoundAfterFirstHit();

    if (PlayerStatus.PLAY_LAST_BATTLE.equals(symphogearPlayer.getStatus())) {
      symphogearPlayer.playLastBattle();
    }

    int continuousCount = 1;
    while (!PlayerStatus.FINISH.equals(symphogearPlayer.getStatus())) {
      ++continuousCount;
      symphogearPlayer.playRoundAllocationAndRound();
      symphogearPlayer.playGx();
    }

    int resultBall = symphogearPlayer.getHavingBall();

    PrizeRateInformation prizeRateInformation
        = new PrizeRateInformation(1000, 2500, 8000);
    PachinkoStore pachinkoStore
        = PachinkoStore.of(prizeRateInformation, hitInputModel.getChangeRate().doubleValue());

    Prize resultPrize = pachinkoStore.convertBallToPrize(resultBall);

    ConversionCenter conversionCenter = new ConversionCenter(prizeRateInformation);

    int resultYen = conversionCenter.convertPrizeToYen(resultPrize);

    return HitResultModel.builder()
                         .investmentYen(firstHitInformation.getFirstHitMoney())
                         .collectionBall(resultBall)
                         .collectionYen(resultYen)
                         .balanceResultYen(resultYen - firstHitInformation.getFirstHitMoney())
                         .firstHit(firstHitInformation.getFirstHitRound())
                         .continuousHitCount(continuousCount)
                         .roundAllocations(symphogearPlayer.getRoundHistory())
                         .build();
  }
}
