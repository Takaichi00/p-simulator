package com.takaichi00.infrastructure.symphogear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "p_result")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HitResultEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "p_name")
  private String pName;

  @Column(name = "first_hit_round")
  private Integer firstHitRound;

  @Column(name = "first_hit_money")
  private Integer firstHitMoney;
}
