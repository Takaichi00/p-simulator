package com.takaichi00.application.symphogear;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class HitResultModel {
  private final Integer investmentYen;
  private final Integer collectionBall;
  private final Integer collectionYen;
  private final Integer balanceResultYen;
  private final Integer firstHit;
  private final Integer continuousHitCount;
  private final List<String> roundAllocations;
}
