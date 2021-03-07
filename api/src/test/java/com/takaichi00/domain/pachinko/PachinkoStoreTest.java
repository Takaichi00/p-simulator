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
      Prize expected = new Prize(1, 0, 0);
      Prize actual = pachinkoStore.convertBallToPrize(250);
      assertEquals(expected, actual);
    }

    @Test
    void _249玉は景品と交換できない() {
      Prize expected = new Prize(0, 0, 0);
      Prize actual = pachinkoStore.convertBallToPrize(249);
      assertEquals(expected, actual);
    }

    @Test
    void _251玉は小1つと交換する() {
      Prize expected = new Prize(1, 0, 0);
      Prize actual = pachinkoStore.convertBallToPrize(251);
      assertEquals(expected, actual);
    }

    @Test
    void _625玉は中1つと交換する() {
      Prize expected = new Prize(0, 1, 0);
      Prize actual = pachinkoStore.convertBallToPrize(625);
      assertEquals(expected, actual);
    }

    @Test
    void _624玉は小1つと交換する() {
      Prize expected = new Prize(1, 0, 0);
      Prize actual = pachinkoStore.convertBallToPrize(624);
      assertEquals(expected, actual);
    }

    @Test
    void _626玉は中1つと交換する() {
      Prize expected = new Prize(0, 1, 0);
      Prize actual = pachinkoStore.convertBallToPrize(626);
      assertEquals(expected, actual);
    }

    @Test
    void _2000玉は大1つと交換する() {
      Prize expected = new Prize(0, 0, 1);
      Prize actual = pachinkoStore.convertBallToPrize(2000);
      assertEquals(expected, actual);
    }

  }
}
