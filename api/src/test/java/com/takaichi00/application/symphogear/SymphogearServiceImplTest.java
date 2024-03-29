package com.takaichi00.application.symphogear;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.takaichi00.domain.symphogear.FirstHitInformation;
import com.takaichi00.domain.symphogear.PachinkoPlayerCreator;
import com.takaichi00.domain.symphogear.PlayerStatus;
import com.takaichi00.domain.symphogear.SymphogearMachine;
import com.takaichi00.domain.symphogear.SymphogearPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

  @Nested
  @DisplayName("1回の結果取得に関するテスト")
  class onceInformation {
    @Test
    void _1回の初当たり情報を取得して返却することができる() {
      SymphogearPlayer spySymphogearPlayer = spy(SymphogearPlayer.of(new SymphogearMachine(), 20));
      when(pachinkoPlayerCreator.createPlayer(20)).thenReturn(spySymphogearPlayer);

      FirstHitInformation mockFirstHitInformation = FirstHitInformation.builder()
          .firstHitBall(1250)
          .firstHitMoney(5000)
          .firstHitRound(100)
          .build();
      when(spySymphogearPlayer.playSymphogearUntilFirstHit()).thenReturn(mockFirstHitInformation);
      when(spySymphogearPlayer.getHavingBall()).thenReturn(2000);
      doNothing().when(spySymphogearPlayer).playRoundAllocationAndRound();
      doNothing().when(spySymphogearPlayer).playGx();
      when(spySymphogearPlayer.getStatus()).thenReturn(PlayerStatus.PLAY_LAST_BATTLE,
          PlayerStatus.PLAY_GX_ALLOCATION_AND_ROUND,
          PlayerStatus.PLAY_GX_ALLOCATION_AND_ROUND,
          PlayerStatus.FINISH);

      when(spySymphogearPlayer.getRoundHistory()).thenReturn(Arrays.asList("4R", "10R", "10R"));
      when(spySymphogearPlayer.getContinuousCount()).thenReturn(3);

      HitInputModel hitInputModel = HitInputModel.builder()
          .rotationRatePer1000yen(20)
          .changeRate(BigDecimal.valueOf(3.6))
          .ballReductionRate(BigDecimal.valueOf(0.05))
          .build();

      HitResultModel actual = testTarget.getHitInformation(hitInputModel);

      assertEquals(5000, actual.getInvestmentYen());
      assertEquals(2000, actual.getCollectionBall());
      assertEquals(7000, actual.getCollectionYen());
      assertEquals(2000, actual.getBalanceResultYen());
      assertEquals(100, actual.getFirstHit());
      assertEquals(3, actual.getContinuousHitCount());
      assertEquals(Arrays.asList("4R", "10R", "10R"), actual.getRoundAllocations());
    }
  }

  @Nested
  @DisplayName("複数回概要結果取得に関するテスト")
  class summaryInformation {
    @Test
    void _0回の値取得をしようとすると例外が発生する() {
      assertThrows(
          RuntimeException.class, () -> testTarget.getHitAvgInformation(null, 0)
      );
    }

    @Test
    void _3回の値取得をしようとすると例外が発生する() {

    }
  }

  @Test
  void Streamのmaxとminの学習用テスト() {
    List<Integer> investmentYenList = new ArrayList<>();
    investmentYenList.add(5);
    investmentYenList.add(3);
    investmentYenList.add(1);
    investmentYenList.add(2);

    Optional<Integer> resultMax = investmentYenList.stream().max(Comparator.naturalOrder());
    assertEquals(5, resultMax.get().intValue());

    Optional<Integer> resultMin = investmentYenList.stream().min(Comparator.naturalOrder());
    assertEquals(1, resultMin.get().intValue());

    double resultAvg = investmentYenList.stream().mapToDouble(d -> d).average().orElse(0);
    assertEquals(2.75, resultAvg);
  }
}
