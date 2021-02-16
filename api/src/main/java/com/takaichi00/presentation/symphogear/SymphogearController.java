package com.takaichi00.presentation.symphogear;

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

    HitResultModel hitResultModel = symphogearService.getHitInformation();

    SymphogearResultResponse response =
        SymphogearResultResponse.builder()
                                .investmentYen(5000)
                                .collectionBall(2000)
                                .collectionYen(7200)
                                .balanceResultYen(2200)
                                .firstHit(hitResultModel.getFirstHit())
                                .continuousHitCount(hitResultModel.getContinuousHitCount())
                                .roundAllocations(hitResultModel.getRoundAllocations())
                                .build();
    return Response.ok(response).build();
  }
}
