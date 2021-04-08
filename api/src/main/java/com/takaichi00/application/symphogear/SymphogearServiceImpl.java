package com.takaichi00.application.symphogear;

import com.takaichi00.domain.pachinko.ConversionCenter;
import com.takaichi00.domain.pachinko.PachinkoStore;
import com.takaichi00.domain.pachinko.Prize;
import com.takaichi00.domain.pachinko.PrizeRateInformation;
import com.takaichi00.domain.symphogear.FirstHitInformation;
import com.takaichi00.domain.symphogear.PachinkoPlayerCreator;
import com.takaichi00.domain.symphogear.SymphogearPlayer;
import java.util.Arrays;
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
    symphogearPlayer.playLastBattle();

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
                         .continuousHitCount(3)
                         .roundAllocations(Arrays.asList("4R", "10R", "10R"))
                         .build();
  }
}
