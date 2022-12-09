package com.hm.digital.twin.vo;

import org.springframework.data.jpa.domain.Specification;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.ElectronicCall;

import lombok.Data;

@Data
public class ElectronicCallVO extends BaseQuery<ElectronicCall> {

  /**
   * 类型
   */
  @QueryCondition(func = MatchType.equal)
  private String type;

  /**
   * 监所编号
   */
  @QueryCondition(func = MatchType.equal)
  private String prisonId;


  @Override
  public Specification<ElectronicCall> toSpec() {
    return super.toSpecWithAnd();
  }

}
