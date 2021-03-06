package com.takaichi00.domain.pachinko;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PachinkoStoreTest {

  @Nested
  @DisplayName("景品の価格が小:1000円/中:2500円/大:8000円,換金率が1玉当たり4円だった場合")
  class case1 {

    private PachinkoStore pachinkoStore = PachinkoStore.of(new PrizeRateInformation(1000, 2500, 8000), 4.0);

    @Test
    void _250玉は小1つと交換する() {
      Prize expected = Prize.builder()
                            .smallPrizeAmount(1)
                            .build();
      Prize actual = pachinkoStore.convertBallToPrize(250);
      assertEquals(expected, actual);
    }

  }
}
