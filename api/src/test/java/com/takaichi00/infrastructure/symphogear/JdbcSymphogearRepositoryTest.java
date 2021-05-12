package com.takaichi00.infrastructure.symphogear;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.junit.jupiter.api.Assertions.*;

class JdbcSymphogearRepositoryTest {

  private static final Operation DELETE_ALL = deleteAllFrom("p_result");
  private static final Operation INSERT_DEFAULT = insertInto("p_result")
      .columns("id", "p_name")
      .values("1", "symphogear")
      .build();
  Operation operation = sequenceOf(DELETE_ALL, INSERT_DEFAULT);

  @Test
  @Disabled
  void ゲームの結果情報を保存することができる() {
    DbSetup dbSetup = new DbSetup(new DriverManagerDestination("jdbc:h2:mem:cid", "username", "password"), operation);
    dbSetup.launch();
  }
}
