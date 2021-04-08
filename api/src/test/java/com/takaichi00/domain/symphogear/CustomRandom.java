package com.takaichi00.domain.symphogear;

import java.util.Random;

public class CustomRandom extends Random {

  private final int returnInteger;

  CustomRandom(int returnInteger) {
    this.returnInteger = returnInteger;
  }

  @Override
  public int nextInt(int bits) {
    return returnInteger;
  }
}
