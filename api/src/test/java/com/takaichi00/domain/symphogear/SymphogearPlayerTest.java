package com.takaichi00.domain.symphogear;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SymphogearPlayerTest {

  @Nested
  class Playerは1000円あたり20回転でシンフォチアを打つ能力をもつ場合 {

    @Test
    void 通常時のシンフォギアを大当りするまで打ち_初当たりにかかったお金と回転数を取得できる() {
      // setup
      SymphogearMachine symphogearMachine = spy(new SymphogearMachine());

      List<Boolean> hitMockBoolean = new ArrayList<>();

      for (int i = 0; i < 198; ++i) {
        hitMockBoolean.add(false);
      }
      hitMockBoolean.add(true);

      when(symphogearMachine.drawLots()).thenReturn(false, hitMockBoolean.toArray(new Boolean[hitMockBoolean.size()]));

      SymphogearPlayer testTarget = SymphogearPlayer.of(symphogearMachine, 20);
      FirstHitInformation expected = FirstHitInformation.of(10000, 200);

      // execute
      testTarget.playSymphogear();
      FirstHitInformation actual = testTarget.getFirstImformation();

      // assert
      assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
      assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
    }

  }
}
