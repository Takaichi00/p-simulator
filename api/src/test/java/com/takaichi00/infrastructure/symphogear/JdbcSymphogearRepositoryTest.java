package com.takaichi00.infrastructure.symphogear;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import com.takaichi00.domain.symphogear.SymphogearRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class JdbcSymphogearRepositoryTest {

  @Inject
  SymphogearRepository symphogearRepository;

  @Inject
  EntityManager entityManager;

  private static final Operation DELETE_ALL = deleteAllFrom("p_result");
  private static final Operation INSERT_DEFAULT = insertInto("p_result")
                                                    .columns("id", "p_name", "first_hit_round", "investment_yen", "collection_ball")
                                                    .values("1", "symphogear", 50, 1000, 3000)
                                                    .build();
  Operation operation = sequenceOf(DELETE_ALL, INSERT_DEFAULT);

  @Test
  void ゲームの結果情報の初当たり回転数を保存することができる() {
    DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:mem:test", "testuser", "testpass"), operation);
    dbSetup.launch();

    symphogearRepository.save(100);

    TypedQuery<HitResultEntity> query
        = entityManager.createQuery("FROM HitResultEntity WHERE id = :id", HitResultEntity.class).setParameter("id", 2);
    HitResultEntity actual = query.getSingleResult();

    assertEquals(100, actual.getFirstHitRound());

  }
}
