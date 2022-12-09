package com.hm.digital.twin.vo;

import org.springframework.data.jpa.domain.Specification;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.PrisonerRecord;

import lombok.Data;

@Data
public class PrisonerRecordListVO extends BaseQuery<PrisonerRecord> {

  /**
   * 请求参数
   */
  @QueryCondition(func = MatchType.equal)
  private String requiredParameter;


  /**
   * 监所编号
   */
  @QueryCondition(func = MatchType.equal)
  private String prisonId;

  @Override
  public Specification<PrisonerRecord> toSpec() {
    return super.toSpecWithAnd();
  }

}
