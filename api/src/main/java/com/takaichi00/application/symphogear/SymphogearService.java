package com.takaichi00.application.symphogear;

public interface SymphogearService {

  HitResultModel getHitInformation(HitInputModel hitInputModel);

  HitAvgResultModel getHitAvgInformation(HitInputModel hitInputModel, int hitLoopCount);
}
