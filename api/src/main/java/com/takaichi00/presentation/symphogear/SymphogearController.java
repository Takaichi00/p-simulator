package com.takaichi00.presentation.symphogear;

import com.takaichi00.application.symphogear.HitSummaryResultModel;
import com.takaichi00.application.symphogear.HitInputModel;
import com.takaichi00.application.symphogear.HitResultModel;
import com.takaichi00.application.symphogear.SymphogearService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/symphogear")
public class SymphogearController {

  @Inject
  SymphogearService symphogearService;

  @POST
  @Path("/balance")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response balance(SymphogearRequest symphogearRequest) {
    HitInputModel hitInputModel
          = HitInputModel.builder()
                    .rotationRatePer1000yen(symphogearRequest.getRotationRatePer1000yen())
                    .changeRate(symphogearRequest.getChangeRate())
                    .ballReductionRate(symphogearRequest.getBallReductionRate())
                    .build();

    HitResultModel hitResultModel = symphogearService.getHitInformation(hitInputModel);

    SymphogearResultResponse response =
        SymphogearResultResponse.builder()
                                .investmentYen(hitResultModel.getInvestmentYen())
                                .collectionBall(hitResultModel.getCollectionBall())
                                .collectionYen(hitResultModel.getCollectionYen())
                                .balanceResultYen(hitResultModel.getBalanceResultYen())
                                .firstHit(hitResultModel.getFirstHit())
                                .continuousHitCount(hitResultModel.getContinuousHitCount())
                                .roundAllocations(hitResultModel.getRoundAllocations())
                                .build();
    return Response.ok(response).build();
  }

  @POST
  @Path("/balance/{count}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response balance(SymphogearRequest symphogearRequest, @PathParam("count") int count) {

    if (count <= 0) {
      throw new RuntimeException();
    }

    HitInputModel hitInputModel
        = HitInputModel.builder()
        .rotationRatePer1000yen(symphogearRequest.getRotationRatePer1000yen())
        .changeRate(symphogearRequest.getChangeRate())
        .ballReductionRate(symphogearRequest.getBallReductionRate())
        .build();

    List<HitResultModel> hitResultModelList = new ArrayList<>();

    for (int i=0; i < count; ++i) {
      hitResultModelList.add(symphogearService.getHitInformation(hitInputModel));
    }

    List<SymphogearResultResponse> symphogearResultResponseList = new ArrayList<>();

    for (HitResultModel hitResultModel : hitResultModelList) {
      symphogearResultResponseList.add(
          SymphogearResultResponse.builder()
                                  .investmentYen(hitResultModel.getInvestmentYen())
                                  .collectionBall(hitResultModel.getCollectionBall())
                                  .collectionYen(hitResultModel.getCollectionYen())
                                  .balanceResultYen(hitResultModel.getBalanceResultYen())
                                  .firstHit(hitResultModel.getFirstHit())
                                  .continuousHitCount(hitResultModel.getContinuousHitCount())
                                  .roundAllocations(hitResultModel.getRoundAllocations())
                                  .build()
      );
    }
    return Response.ok(symphogearResultResponseList).build();
  }

  @POST
  @Path("/balance/summary/{count}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response balanceSummary(SymphogearRequest symphogearRequest, @PathParam("count") int count) {

    if (count <= 0) {
      throw new RuntimeException();
    }

    HitInputModel hitInputModel
        = HitInputModel.builder()
        .rotationRatePer1000yen(symphogearRequest.getRotationRatePer1000yen())
        .changeRate(symphogearRequest.getChangeRate())
        .ballReductionRate(symphogearRequest.getBallReductionRate())
        .build();

    HitSummaryResultModel result = symphogearService.getHitAvgInformation(hitInputModel, count);

    SymphogearResultResponse avgResponse =
        SymphogearResultResponse.builder()
                                .investmentYen(result.getAvgHitResultModel().getInvestmentYen())
                                .collectionBall(result.getAvgHitResultModel().getCollectionBall())
                                .collectionYen(result.getAvgHitResultModel().getCollectionYen())
                                .balanceResultYen(result.getAvgHitResultModel().getBalanceResultYen())
                                .firstHit(result.getAvgHitResultModel().getFirstHit())
                                .continuousHitCount(result.getAvgHitResultModel().getContinuousHitCount())
                                .build();

    SymphogearResultResponse maxResponse =
        SymphogearResultResponse.builder()
                                .investmentYen(result.getMaxHitResultModel().getInvestmentYen())
                                .collectionBall(result.getMaxHitResultModel().getCollectionBall())
                                .collectionYen(result.getMaxHitResultModel().getCollectionYen())
                                .balanceResultYen(result.getMaxHitResultModel().getBalanceResultYen())
                                .firstHit(result.getMaxHitResultModel().getFirstHit())
                                .continuousHitCount(result.getMaxHitResultModel().getContinuousHitCount())
                                .build();

    SymphogearResultResponse minResponse =
        SymphogearResultResponse.builder()
                                .investmentYen(result.getMinHitResultModel().getInvestmentYen())
                                .collectionBall(result.getMinHitResultModel().getCollectionBall())
                                .collectionYen(result.getMinHitResultModel().getCollectionYen())
                                .balanceResultYen(result.getMinHitResultModel().getBalanceResultYen())
                                .firstHit(result.getMinHitResultModel().getFirstHit())
                                .continuousHitCount(result.getMinHitResultModel().getContinuousHitCount())
                                .build();

    SymphogearSummaryResultResponse summaryResponse = SymphogearSummaryResultResponse.builder()
        .averageResponse(avgResponse)
        .maxResponse(maxResponse)
        .minResponse(minResponse)
        .build();


    return Response.ok(summaryResponse).build();
  }
}
