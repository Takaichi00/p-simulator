package com.takaichi00.application.symphogear;

import static org.junit.jupiter.api.Assertions.*;

import com.takaichi00.domain.symphogear.PachinkoPlayerCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

class SymphogearServiceImplTest {

  @InjectMocks
  SymphogearServiceImpl testTarget;

  @Mock
  PachinkoPlayerCreator pachinkoPlayerCreator;

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
    HitInputModel hitInputModel = HitInputModel.builder()
                                               .rotationRatePer1000yen(20)
                                               .changeRate(BigDecimal.valueOf(3.6))
                                               .ballReductionRate(BigDecimal.valueOf(0.05))
                                               .build();

    HitResultModel actual = testTarget.getHitInformation(hitInputModel);

    assertEquals(5000, actual.getInvestmentYen());
    assertEquals(2000, actual.getCollectionBall());
    assertEquals(7200, actual.getCollectionYen());
    assertEquals(2200, actual.getBalanceResultYen());
    assertEquals(100, actual.getFirstHit());
    assertEquals(3, actual.getContinuousHitCount());
    assertEquals(Arrays.asList("4R", "10R", "10R"), actual.getRoundAllocations());
  }
}
