package com.takaichi00.domain.symphogear;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class SymphogearPlayerTest {

  @Nested
  class Playerは1000円あたり20回転でシンフォチアを打つ能力をもつ場合 {

    private static final int ROUND_PER_1000YEN = 20;

    @Nested
    class 通常時のシンフォギアを大当りするまで打ち_初当たりにかかったお金と回転数を取得できる {

      @ParameterizedTest
      @CsvSource({
        "10000, 200",
        "7500, 150",
        "5000, 100",
        "2500, 50",
        "100, 2",
      })
      void X円投資Y回転であたった場合(int firstHitMoney, int firstHitRound) {
        // setup
        SymphogearMachine symphogearMachine = spy(new SymphogearMachine());

        List<Boolean> hitMockBoolean = new ArrayList<>();

        for (int i = 0; i < firstHitRound - 2; ++i) {
          hitMockBoolean.add(false);
        }
        hitMockBoolean.add(true);

        when(symphogearMachine.drawLots()).thenReturn(false, hitMockBoolean.toArray(new Boolean[hitMockBoolean.size()]));

        SymphogearPlayer testTarget = SymphogearPlayer.of(symphogearMachine, ROUND_PER_1000YEN);
        FirstHitInformation expected = FirstHitInformation.of(firstHitMoney, firstHitRound);

        // execute
        testTarget.playSymphogear();
        FirstHitInformation actual = testTarget.getFirstImformation();

        // assert
        assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
        assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
      }

      @Test
      void _50円投資1回転であたった場合() {
        // setup
        SymphogearMachine symphogearMachine = spy(new SymphogearMachine());


        when(symphogearMachine.drawLots()).thenReturn(true);

        SymphogearPlayer testTarget = SymphogearPlayer.of(symphogearMachine, ROUND_PER_1000YEN);
        FirstHitInformation expected = FirstHitInformation.of(50, 1);

        // execute
        testTarget.playSymphogear();
        FirstHitInformation actual = testTarget.getFirstImformation();

        // assert
        assertEquals(expected.getFirstHitMoney(), actual.getFirstHitMoney());
        assertEquals(expected.getFirstHitRound(), actual.getFirstHitRound());
      }
    }
  }
}
