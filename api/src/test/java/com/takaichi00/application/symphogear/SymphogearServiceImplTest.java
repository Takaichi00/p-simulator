package com.takaichi00.application.symphogear;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

class SymphogearServiceImplTest {

  @InjectMocks
  private SymphogearServiceImpl testTarget;
  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void closeService() throws Exception {
    closeable.close();
  }

  @Test
  void _1回の初当たり情報を取得して返却することができる() {
    HitResultModel excected = HitResultModel.builder()
                                            .investmentYen(5000)
                                            .collectionBall(2000)
                                            .collectionYen(7200)
                                            .balanceResultYen(2200)
                                            .firstHit(100)
                                            .continuousHitCount(3)
                                            .roundAllocations(Arrays.asList("4R", "10R", "10R"))
                                            .build();

    HitResultModel actual = testTarget.getHitInformation();

    assertEquals(excected, actual);
  }
}
