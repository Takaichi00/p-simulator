package com.takaichi00.presentation.symphogear;

import java.util.Arrays;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/v1/symphogear")
public class SymphogearController {

  @POST
  @Path("/balance/{count}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response balance(@PathParam Integer count) {
    SymphogearResultResponse response =
        SymphogearResultResponse.builder()
                                .investmentYen(5000)
                                .collectionBall(2000)
                                .collectionYen(7200)
                                .balanceResultYen(2200)
                                .firstHit(100)
                                .continuousHitCount(3)
                                .roundAllocations(Arrays.asList("4R", "10R", "10R"))
                                .build();
    return Response.ok(response).build();
  }
}
