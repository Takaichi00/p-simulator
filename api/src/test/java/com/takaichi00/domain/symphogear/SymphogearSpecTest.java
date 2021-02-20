package com.takaichi00.domain.symphogear;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymphogearSpecTest {

  @Test
  @DisplayName("1回抽選して 1/199.8 を引き当てなかった場合はハズレが取得できる")
  void _1回抽選してxxxを引き当てなかった場合はハズレが取得できる() {
    // setup
    Boolean expected = false;
    SymphogearSpec testTarget = new SymphogearSpec();

    // execute
    Boolean actual = testTarget.drawLots();

    // assert
    assertEquals(expected, actual);
  }

}

