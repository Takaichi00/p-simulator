package com.takaichi00.presentation.symphogear;

import com.takaichi00.application.symphogear.HitInputModel;
import com.takaichi00.application.symphogear.HitResultModel;
import com.takaichi00.application.symphogear.SymphogearService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/v1/symphogear")
public class SymphogearController {

  @Inject
  SymphogearService symphogearService;

  @POST
  @Path("/balance/{count}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response balance(SymphogearRequest symphogearRequest, @PathParam Integer count) {
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
}
