package com.takaichi00.domain.symphogear;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SymphogearPlayerCreator implements PachinkoPlayerCreator {

  @Override
  public SymphogearPlayer createPlayer(int rotationRatePer1000yen) {
    SymphogearMachine symphogearMachine = new SymphogearMachine();
    return SymphogearPlayer.of(symphogearMachine, rotationRatePer1000yen);
  }
}
