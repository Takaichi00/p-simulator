package com.takaichi00.domain.symphogear;

import com.takaichi00.domain.pachinko.ConversionCenter;
import com.takaichi00.domain.pachinko.Prize;
import com.takaichi00.domain.pachinko.PrizeRateInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionCenterTest {
  @DisplayName("小景品/中景品/大景品の値段が1000円/2500円/8000円の場合")
  @Nested
  class case1 {

    private ConversionCenter testTarget = new ConversionCenter(new PrizeRateInformation(1000, 2500, 8000));

    @Test
    void 小1つを渡すと1000円を払い出す() {
      int exptected = 1000;
      int actual = testTarget.convertPrizeToYen(new Prize(1, 0, 0));
      assertEquals(exptected, actual);
    }

    @Test
    void 中1つを渡すと2500円を払い出す() {
      int exptected = 2500;
      int actual = testTarget.convertPrizeToYen(new Prize(0, 1, 0));
      assertEquals(exptected, actual);
    }

    @Test
    void 大1つを渡すと8000円を払い出す() {
      int exptected = 8000;
      int actual = testTarget.convertPrizeToYen(new Prize(0, 0, 1));
      assertEquals(exptected, actual);
    }

    @Test
    void 小1つ中1つ大1つを渡すと11500円を払い出す() {
      int exptected = 11500;
      int actual = testTarget.convertPrizeToYen(new Prize(1, 1, 1));
      assertEquals(exptected, actual);
    }

    @Test
    void 小2つ中2つ大2つを渡すと23000円を払い出す() {
      int exptected = 23000;
      int actual = testTarget.convertPrizeToYen(new Prize(2, 2, 2));
      assertEquals(exptected, actual);
    }

  }
}
