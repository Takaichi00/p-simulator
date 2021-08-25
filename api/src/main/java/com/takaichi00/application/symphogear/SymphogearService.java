package com.takaichi00.application.symphogear;

public interface SymphogearService {

  HitResultModel getHitInformation(HitInputModel hitInputModel);

  HitSummaryResultModel getHitAvgInformation(HitInputModel hitInputModel, int hitLoopCount);
}
