package com.takaichi00.infrastructure.symphogear;

import com.takaichi00.domain.symphogear.SymphogearRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class JdbcSymphogearRepository implements SymphogearRepository {

  @Inject
  EntityManager entityManager;

  @Override
  @Transactional
  public void save(int i) {
    entityManager.persist(ResultEntity.builder()
                                      .pName("symphogear")
                                      .firstHit(100)
                                      .build());
  }
}
