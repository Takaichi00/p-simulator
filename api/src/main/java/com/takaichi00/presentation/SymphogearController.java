package com.takaichi00.presentation;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/v1/symphogear")
public class SymphogearController {

  @POST
  @Path("/balance/{count}")
  @Produces(MediaType.APPLICATION_JSON)
  public String searchBook(@PathParam Integer count) {
    return "ok";
  }
}
