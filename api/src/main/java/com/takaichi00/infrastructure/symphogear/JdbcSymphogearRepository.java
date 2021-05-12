package com.takaichi00.infrastructure.symphogear;

import com.takaichi00.domain.symphogear.SymphogearRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class JdbcSymphogearRepository implements SymphogearRepository {
//  @Inject
//  EntityManager entityManager;
}
