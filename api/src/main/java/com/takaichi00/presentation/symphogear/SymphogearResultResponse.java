package com.takaichi00.presentation.symphogear;

import java.util.List;
import javax.json.bind.annotation.JsonbProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymphogearResultResponse {
  @JsonbProperty(value = "investment_yen")
  private Integer investmentYen;

  @JsonbProperty(value = "collection_ball")
  private Integer collectionBall;

  @JsonbProperty(value = "collection_yen")
  private Integer collectionYen;

  @JsonbProperty(value = "balance_result_yen")
  private Integer balanceResultYen;

  @JsonbProperty(value = "first_hit")
  private Integer firstHit;

  @JsonbProperty(value = "continuous_hit_count")
  private Integer continuousHitCount;

  @JsonbProperty(value = "round_allocations")
  private List<String> roundAllocations;
}
