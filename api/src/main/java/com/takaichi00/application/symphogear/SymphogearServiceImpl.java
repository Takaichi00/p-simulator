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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    while (!PlayerStatus.FINISH.equals(symphogearPlayer.getStatus())) {
      symphogearPlayer.playRoundAllocationAndRound();
      symphogearPlayer.playGx();
    }


    PrizeRateInformation prizeRateInformation
        = new PrizeRateInformation(1000, 2500, 8000);
    PachinkoStore pachinkoStore
        = PachinkoStore.of(prizeRateInformation, hitInputModel.getChangeRate().doubleValue());

    int resultBall = symphogearPlayer.getHavingBall();
    Prize resultPrize = pachinkoStore.convertBallToPrize(resultBall);

    ConversionCenter conversionCenter = new ConversionCenter(prizeRateInformation);

    int resultYen = conversionCenter.convertPrizeToYen(resultPrize);

    return HitResultModel.builder()
                         .investmentYen(firstHitInformation.getFirstHitMoney())
                         .collectionBall(resultBall)
                         .collectionYen(resultYen)
                         .balanceResultYen(resultYen - firstHitInformation.getFirstHitMoney())
                         .firstHit(firstHitInformation.getFirstHitRound())
                         .continuousHitCount(symphogearPlayer.getContinuousCount())
                         .roundAllocations(symphogearPlayer.getRoundHistory())
                         .build();
  }

  @Override
  public HitSummaryResultModel getHitAvgInformation(HitInputModel hitInputModel, int hitLoopCount) {
    if (hitLoopCount < 1) {
      throw new RuntimeException();
    }

    List<Integer> investmentYenList = new ArrayList<>();

    for (int i = 0; i < hitLoopCount; ++i) {
      HitResultModel hitResultModel = getHitInformation(hitInputModel);
      investmentYenList.add(hitResultModel.getInvestmentYen());
    }

    HitResultModel avgResult = HitResultModel.builder()
                                             .investmentYen((int)investmentYenList.stream()
                                                 .mapToDouble(d -> d).average().orElse(0))
                                             .collectionBall(null)
                                             .collectionYen(null)
                                             .balanceResultYen(null)
                                             .firstHit(null)
                                             .continuousHitCount(null)
                                             .roundAllocations(null)
                                             .build();

    HitResultModel maxResult = HitResultModel.builder()
                                             .investmentYen(investmentYenList.stream()
                                                 .max(Comparator.naturalOrder()).get())
                                             .collectionBall(null)
                                             .collectionYen(null)
                                             .balanceResultYen(null)
                                             .firstHit(null)
                                             .continuousHitCount(null)
                                             .roundAllocations(null)
                                             .build();

    HitResultModel minResult = HitResultModel.builder()
                                             .investmentYen(investmentYenList
                                                 .stream().min(Comparator.naturalOrder()).get())
                                             .collectionBall(null)
                                             .collectionYen(null)
                                             .balanceResultYen(null)
                                             .firstHit(null)
                                             .continuousHitCount(null)
                                             .roundAllocations(null)
                                             .build();

    return HitSummaryResultModel.builder()
                                .avgHitResultModel(avgResult)
                                .maxHitResultModel(maxResult)
                                .minHitResultModel(minResult)
                                .build();

  }
}
