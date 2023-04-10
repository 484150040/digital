package com.hm.digital.twin.vo;

import org.springframework.data.jpa.domain.Specification;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.UserRole;

import lombok.Data;

@Data
public class UserRoleVo extends BaseQuery<UserRole> {

  /**
   * 作业编号
   */
  @QueryCondition(func = MatchType.equal)
  private String id;

  /**
   * 用户id
   */
  @QueryCondition(func = MatchType.equal)
  private String userId;

  /**
   * 角色id
   */
  @QueryCondition(func = MatchType.equal)
  private String roleId;

  @Override
  public Specification<UserRole> toSpec() {
    return super.toSpecWithAnd();
  }
}
