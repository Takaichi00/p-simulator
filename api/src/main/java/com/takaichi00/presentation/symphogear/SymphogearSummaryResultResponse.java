package com.takaichi00.presentation.symphogear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymphogearSummaryResultResponse {

  @JsonbProperty(value = "average")
  private SymphogearResultResponse averageResponse;

  @JsonbProperty(value = "max")
  private SymphogearResultResponse maxResponse;

  @JsonbProperty(value = "min")
  private SymphogearResultResponse minResponse;
}
