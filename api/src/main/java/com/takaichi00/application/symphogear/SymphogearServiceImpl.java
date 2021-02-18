package com.takaichi00.application.symphogear;

import java.util.Arrays;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SymphogearServiceImpl implements SymphogearService {
  @Override
  public HitResultModel getHitInformation() {
    return HitResultModel.builder()
                         .investmentYen(5000)
                         .collectionBall(2000)
                         .collectionYen(7200)
                         .balanceResultYen(2200)
                         .firstHit(100)
                         .continuousHitCount(3)
                         .roundAllocations(Arrays.asList("4R", "10R", "10R"))
                         .build();
  }
}
