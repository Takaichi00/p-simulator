package com.takaichi00.domain.pachinko;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PachinkoStoreTest {

  @Nested
  @DisplayName("景品の価格が小:1000円/中:2500円/大:8000円,換金率が1玉当たり4円だった場合")
  class case1 {

    private final PachinkoStore pachinkoStore = PachinkoStore.of(new PrizeRateInformation(1000, 2500, 8000), 4.0);

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
    void _624玉は小2つと交換する() {
      Prize expected = new Prize(2, 0, 0);
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

    @Test
    void _4000玉は大2つと交換する() {
      Prize expected = new Prize(0, 0, 2);
      Prize actual = pachinkoStore.convertBallToPrize(4000);
      assertEquals(expected, actual);
    }

    @Test
    void _1999玉は中3つと交換する() {
      Prize expected = new Prize(0, 3, 0);
      Prize actual = pachinkoStore.convertBallToPrize(1999);
      assertEquals(expected, actual);
    }

    @Test
    void _875玉は小1つと中1つと交換する() {
      Prize expected = new Prize(1, 1, 0);
      Prize actual = pachinkoStore.convertBallToPrize(875);
      assertEquals(expected, actual);
    }

    @Test
    void _2250玉は小1つと大1つと交換する() {
      Prize expected = new Prize(1, 0, 1);
      Prize actual = pachinkoStore.convertBallToPrize(2250);
      assertEquals(expected, actual);
    }

    @Test
    void _2625玉は中1つと大1つと交換する() {
      Prize expected = new Prize(0, 1, 1);
      Prize actual = pachinkoStore.convertBallToPrize(2650);
      assertEquals(expected, actual);
    }

    @Test
    void _2875玉は小1つ中1つと大1つと交換する() {
      Prize expected = new Prize(1, 1, 1);
      Prize actual = pachinkoStore.convertBallToPrize(2875);
      assertEquals(expected, actual);
    }
  }

  @Nested
  @DisplayName("景品の価格が小:1500円/中:3000円/大:7500円,換金率が1玉当たり3.6円だった場合")
  class case2 {

    private final PachinkoStore pachinkoStore = PachinkoStore.of(new PrizeRateInformation(1500, 3000, 7500), 3.6);

    @Test
    void _3334玉は小1つ中1つ大1つと交換できる() {
      Prize expected = new Prize(1, 1, 1);
      Prize actual = pachinkoStore.convertBallToPrize(3334);
      assertEquals(expected, actual);
    }

    @Test
    void _3333玉は中1つ大1つと交換できる() {
      Prize expected = new Prize(0, 1, 1);
      Prize actual = pachinkoStore.convertBallToPrize(3333);
      assertEquals(expected, actual);
    }

    @Test
    void _6667玉は小1大3つと交換できる() {
      Prize expected = new Prize(1, 0, 3);
      Prize actual = pachinkoStore.convertBallToPrize(6667);
      assertEquals(expected, actual);
    }
  }
}
