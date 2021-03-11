package com.takaichi00.domain.symphogear;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymphogearPlayerTest {

  @Nested
  class Playerは1000円あたり20回転でシンフォチアを打つ能力をもつ場合 {

    @Test
    void 通常時のシンフォギアを大当りするまで打ち_初当たりにかかったお金と回転数を取得できる() {
      SymphogearPlayer testTarget = SymphogearPlayer.of(20);
      FirstHitInformation expected = FirstHitInformation.of(10000, 200);
      testTarget.playSymphogear();
      FirstHitInformation actual = testTarget.getFirstImformation();
      assertEquals(expected, actual);
    }

  }
}
